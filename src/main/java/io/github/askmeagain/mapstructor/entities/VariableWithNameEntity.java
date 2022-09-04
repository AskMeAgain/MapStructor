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

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + name.getQualifiedName().hashCode();
    result = 31 * result + type.getCanonicalText().hashCode();
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    VariableWithNameEntity p = (VariableWithNameEntity) o;

    return this.getName().getQualifiedName().equals(p.getName().getQualifiedName())
        && this.getType().getCanonicalText().equals(p.getType().getCanonicalText());
  }
}
