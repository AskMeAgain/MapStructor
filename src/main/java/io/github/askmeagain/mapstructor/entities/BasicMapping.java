package io.github.askmeagain.mapstructor.entities;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BasicMapping {

  PsiType parent;
  String target;
  PsiElement expression;

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + expression.getText().hashCode();
    result = 31 * result + target.hashCode();
    result = 31 * result + parent.getCanonicalText().hashCode();
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    BasicMapping p = (BasicMapping) o;

    var textEquals = expression.getText().equals(p.getExpression().getText());
    var targetEquals = target.equals(p.getTarget());
    var parentEquals = parent.getCanonicalText().equals(p.getParent().getCanonicalText());

    return textEquals && targetEquals && parentEquals;
  }

}
