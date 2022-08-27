package io.github.askmeagain.mapstructor.entities;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Value
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MappingMethods {

  List<TargetSourceContainer> mappings;

  @EqualsAndHashCode.Include
  PsiType outputType;

  @Value
  @Builder
  public static class TargetSourceContainer {

    PsiElement source;
    String target;
  }

}
