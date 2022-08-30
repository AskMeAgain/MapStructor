package io.github.askmeagain.mapstructor.visitor;

import com.intellij.psi.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FindLastReferenceExpressionVisitor extends JavaRecursiveElementVisitor {

  @Getter
  private PsiType lastPsiType;

  private final PsiFile psiFile;

  @Override
  public void visitReferenceExpression(PsiReferenceExpression expression) {
    if (expression.getType() != null && lastPsiType == null) {
      lastPsiType = FindTypeVisitor.find(psiFile, expression);
    }
    super.visitReferenceExpression(expression);
  }

  public static PsiType find(PsiElement element, PsiFile psiFile) {
    var instance = new FindLastReferenceExpressionVisitor(psiFile);
    element.accept(instance);
    return instance.getLastPsiType();
  }
}
