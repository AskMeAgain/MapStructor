package io.github.askmeagain.mapstructor.entities;

import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.PsiType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VariableWithNameEntity {

  PsiReferenceExpression name;

  PsiType type;

}
