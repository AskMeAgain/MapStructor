package io.github.askmeagain.mapstructor.entities;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.With;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MapstructMethodEntity {

  boolean mainMappingMethod;
  private List<TargetSourceContainer> mappings;

  @EqualsAndHashCode.Include
  private PsiType outputType;

  private final Set<String> innerMethodBody;

  @Builder.Default
  private Set<VariableWithNameEntity> inputs = new HashSet<>();

  public List<VariableWithNameEntity> calculateDeepInputs() {

    var result = new HashSet<>(inputs);

    var deepMappings = mappings.stream()
        .map(TargetSourceContainer::getRefToOtherMapping)
        .filter(Objects::nonNull)
        .map(MapstructMethodEntity::calculateDeepInputs)
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
  public static class TargetSourceContainer implements Comparable<TargetSourceContainer> {

    PsiElement source;
    String target;
    @With
    PsiType refTargetType;

    MapstructMethodEntity refToOtherMapping;

    boolean isExternalMethod;

    MapstructExternalMethodEntity externalMethodEntity;

    @Override
    public int compareTo(@NotNull MapstructMethodEntity.TargetSourceContainer o) {
      return target.compareTo(o.getTarget());
    }
  }

}
