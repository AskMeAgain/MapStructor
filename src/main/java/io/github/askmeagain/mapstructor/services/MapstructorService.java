package io.github.askmeagain.mapstructor.services;

import com.intellij.psi.PsiClassOwner;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLocalVariable;
import io.github.askmeagain.mapstructor.entities.BasicMapping;
import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.entities.MapstructMethodEntity;
import io.github.askmeagain.mapstructor.iteration.*;
import io.github.askmeagain.mapstructor.visitor.FindInnerMethodBodyVisitor;
import io.github.askmeagain.mapstructor.visitor.FindMainMethodVisitor;
import io.github.askmeagain.mapstructor.visitor.MappingVisitor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MapstructorService {

  private final PsiFile psiFile;
  private List<Iteration> iterations;

  public MapstructorService(PsiFile psiFile) {
    this.psiFile = psiFile;
  }

  @NotNull
  private static List<Iteration> getIterations(PsiFile psiFile, Set<PsiLocalVariable> innerMethodBodies) {
    return List.of(
        new CalcInputIteration(),
        new RefMappingIteration(),
        new ExternalMethodIteration(psiFile, innerMethodBodies),
        new MainMethodIteration()
    );
  }

  public MapStructMapperEntity calculate(int start, int end) {

    var straightMappingList = MappingVisitor.find(psiFile, start, end);
    var innerMethodBodies = FindInnerMethodBodyVisitor.find(psiFile, start, end);

    this.iterations = getIterations(psiFile, innerMethodBodies);


    var dividedByParent = straightMappingList.stream()
        .collect(Collectors.groupingBy(BasicMapping::getParent));

    var result = dividedByParent.entrySet()
        .stream()
        .map(mappingByType -> MapstructMethodEntity.builder()
            .innerMethodBody(innerMethodBodies.stream().map(PsiLocalVariable::getName).collect(Collectors.toSet()))
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
