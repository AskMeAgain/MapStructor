package io.github.askmeagain.mapstructor.entities;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.PsiTreeUtil;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.With;

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

    mappings.stream()
        .filter(x -> x.getRefToOtherMapping() != null)
        .forEach(x -> result.removeIf(variableWithName -> variableWithName.getType().equals(x.getRefTargetType())));

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

    public boolean isExternalMethod() {
      return PsiTreeUtil.getChildOfType(source, PsiMethodCallExpression.class) != null;
    }
  }

}
