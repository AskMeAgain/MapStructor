package io.github.askmeagain.mapstructor.entities;

import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.PsiType;
import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
public class VariableWithNameEntity implements Comparable<VariableWithNameEntity> {

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

  @Override
  public int compareTo(@NotNull VariableWithNameEntity o) {
    return this.getName().getReferenceName().compareTo(o.getName().getReferenceName());
  }
}
