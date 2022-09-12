package io.github.askmeagain.mapstructor.services;

import com.intellij.psi.PsiClassOwner;
import com.intellij.psi.PsiFile;
import io.github.askmeagain.mapstructor.entities.BasicMapping;
import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.entities.MapstructMethodEntity;
import io.github.askmeagain.mapstructor.iteration.*;
import io.github.askmeagain.mapstructor.visitor.FindMainMethodVisitor;
import io.github.askmeagain.mapstructor.visitor.MappingVisitor;

import java.util.List;
import java.util.stream.Collectors;

public class MapstructorService {

  private final PsiFile psiFile;
  private final List<Iteration> iterations;

  public MapstructorService(PsiFile psiFile) {
    this.psiFile = psiFile;
    this.iterations = List.of(
        new CalcInputIteration(),
        new RefMappingIteration(),
        new ExternalMethodIteration(psiFile),
        new MainMethodIteration()
    );
  }

  public MapStructMapperEntity calculate(int start, int end) {

    var straightMappingList = MappingVisitor.find(psiFile, start, end);

    var dividedByParent = straightMappingList.stream()
        .collect(Collectors.groupingBy(BasicMapping::getParent));

    var result = dividedByParent.entrySet()
        .stream()
        .map(mappingByType -> MapstructMethodEntity.builder()
            .outputType(mappingByType.getKey())
            .mappings(mappingByType.getValue()
                .stream()
                .map(mapping -> MapstructMethodEntity.TargetSourceContainer.builder()
                    .target(mapping.getTarget())
                    .source(mapping.getExpression())
                    .build())
                .collect(Collectors.toList()))
            .build())
        .collect(Collectors.toList());

    var packageName = ((PsiClassOwner) psiFile).getPackageName();

    var isReturn = FindMainMethodVisitor.find(psiFile, start, end);

    var mapstructEntity = MapStructMapperEntity.builder()
        .packageName(packageName)
        .includedReturn(isReturn)
        .mappings(result)
        .build();

    //compute all needed data
    iterations.forEach(iteration -> iteration.accept(mapstructEntity));

    return mapstructEntity;
  }

}
