package io.github.askmeagain.mapstructor.visitor;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.PsiType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FindLastReferenceExpressionVisitor extends JavaRecursiveElementVisitor {

  @Getter
  private PsiType lastPsiType;

  @Override
  public void visitReferenceExpression(PsiReferenceExpression expression) {
    if (expression.getType() != null && lastPsiType == null) {
      lastPsiType = expression.getType();
    }
    super.visitReferenceExpression(expression);
  }

  public static PsiType find(PsiElement element) {
    var instance = new FindLastReferenceExpressionVisitor();
    element.accept(instance);
    return instance.getLastPsiType();
  }
}
