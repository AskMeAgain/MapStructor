package io.github.askmeagain.mapstructor.services;

import com.intellij.psi.PsiCodeBlock;
import io.github.askmeagain.mapstructor.entities.BasicMapping;
import io.github.askmeagain.mapstructor.entities.CollectedResult;
import io.github.askmeagain.mapstructor.entities.MappingMethods;
import io.github.askmeagain.mapstructor.visitor.MappingVisitor;

import java.util.stream.Collectors;

public class MapstructorService {

  public CollectedResult calc(PsiCodeBlock codeBlock) {

    var straightMappingList = MappingVisitor.find(codeBlock);;

    var dividedByParent = straightMappingList.stream()
        .collect(Collectors.groupingBy(BasicMapping::getParent));

    var result = dividedByParent.entrySet()
        .stream()
        .map(mappingByType -> MappingMethods.builder()
            .outputType(mappingByType.getKey())
            .mappings(mappingByType.getValue()
                .stream()
                .map(mapping -> MappingMethods.TargetSourceContainer.builder()
                    .target(mapping.getTarget())
                    .source(mapping.getExpression())
                    .build())
                .collect(Collectors.toList()))
            .build())
        .collect(Collectors.toList());

    return CollectedResult.builder()
        .mapperName("TODO")
        .mappings(result)
        .build();
  }
}
