package io.github.askmeagain.mapstructor.entities;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.PsiType;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MappingMethods {

  private List<TargetSourceContainer> mappings;

  @EqualsAndHashCode.Include
  private PsiType outputType;

  @With
  @Builder.Default
  private List<VariableWithName> inputs = new ArrayList<>();

  public List<VariableWithName> calculateDeepInputs() {

    var result = new HashSet<>(inputs);

    var deepMappings = mappings.stream()
        .map(TargetSourceContainer::getRefToOtherMapping)
        .filter(Objects::nonNull)
        .map(MappingMethods::calculateDeepInputs)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());

    result.addAll(deepMappings);

    return new ArrayList<>(result);
  }

  @Data
  @Builder
  public static class TargetSourceContainer {

    PsiElement source;
    String target;
    @With
    PsiType refTargetType;

    MappingMethods refToOtherMapping;
  }

}
