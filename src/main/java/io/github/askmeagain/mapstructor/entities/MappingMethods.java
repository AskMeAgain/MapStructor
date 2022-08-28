package io.github.askmeagain.mapstructor.entities;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.PsiType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

  @Value
  @Builder
  public static class TargetSourceContainer {

    PsiElement source;
    String target;
  }

}
