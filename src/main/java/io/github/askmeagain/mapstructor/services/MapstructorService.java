package io.github.askmeagain.mapstructor.services;

import com.intellij.psi.PsiCodeBlock;
import io.github.askmeagain.mapstructor.entities.CollectedResult;
import io.github.askmeagain.mapstructor.entities.MappingMethods;
import io.github.askmeagain.mapstructor.entities.TargetSourceContainer;
import io.github.askmeagain.mapstructor.entities.V2Mapping;
import io.github.askmeagain.mapstructor.visitor.MappingVisitor;

import java.util.stream.Collectors;

public class MapstructorService {

  public CollectedResult calc(PsiCodeBlock codeBlock) {

    var straightMappingList = MappingVisitor.find(codeBlock);;

    var dividedByParent = straightMappingList.stream()
        .collect(Collectors.groupingBy(V2Mapping::getParent));

    var result = dividedByParent.entrySet()
        .stream()
        .map(kv -> MappingMethods.builder()
            .outputType(kv.getKey())
            .mappings(kv.getValue()
                .stream()
                .map(y -> TargetSourceContainer.builder()
                    .target(y.getTarget())
                    .source(y.getExpression())
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
