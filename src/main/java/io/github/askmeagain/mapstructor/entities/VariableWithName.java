package io.github.askmeagain.mapstructor.entities;

import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.PsiType;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class VariableWithName {

  PsiReferenceExpression name;

  @With
  PsiType type;

}
