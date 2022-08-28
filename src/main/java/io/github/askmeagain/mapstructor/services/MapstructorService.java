package io.github.askmeagain.mapstructor.services;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.askmeagain.mapstructor.entities.BasicMapping;
import io.github.askmeagain.mapstructor.entities.CollectedResult;
import io.github.askmeagain.mapstructor.entities.MappingMethods;
import io.github.askmeagain.mapstructor.entities.VariableWithName;
import io.github.askmeagain.mapstructor.visitor.FindTypeVisitor;
import io.github.askmeagain.mapstructor.visitor.MappingVisitor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MapstructorService {

  private final PsiFile psiFile;

  public CollectedResult calc(PsiCodeBlock codeBlock) {

    var straightMappingList = MappingVisitor.find(codeBlock);

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

    calcInputs(result);

    //types of "outside" variables are not always found
    var fixedInputs = fixInputs(result);

    //we need to "fix" referencing mappings

    return CollectedResult.builder()
        .mapperName("TODO")
        .mappings(fixedInputs)
        .build();
  }

  private List<MappingMethods> fixInputs(List<MappingMethods> result) {
    return result.stream()
        .map(x -> {
          var newList = x.getInputs().stream()
              .map(y -> {
                var type = FindTypeVisitor.find(psiFile, y.getName());
                return y.withType(type);
              })
              .collect(Collectors.toList());
          return x.withInputs(newList);
        })
        .collect(Collectors.toList());
  }

  private void calcInputs(List<MappingMethods> result) {

    for (var method : result) {
      var collect = method.getMappings().stream()
          .map(MappingMethods.TargetSourceContainer::getSource)
          .map(this::findInputs)
          .filter(Objects::nonNull)
          .collect(Collectors.toList());

      method.getInputs().addAll(collect);
    }
  }

  private VariableWithName findInputs(PsiElement element) {

    if (PsiTreeUtil.getChildOfType(element, PsiLiteralExpression.class) != null) {
      return null; //no inputs
    }

    var psiReferenceExpression = PsiTreeUtil.getChildOfType(element, PsiReferenceExpression.class);
    if (psiReferenceExpression != null) {
      return VariableWithName.builder()
          .type(psiReferenceExpression.getType())
          .name(psiReferenceExpression)
          .build();
    }

    return null;
  }
}
