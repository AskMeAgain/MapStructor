package io.github.askmeagain.mapstructor.visitor;

import com.intellij.psi.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FindMethodCallExpressionVisitor extends JavaRecursiveElementVisitor {

  @Getter
  private PsiMethodCallExpression result;

  private final PsiMethodCallExpression toSearchFor;

  public void visitMethodCallExpression(PsiMethodCallExpression expression) {

    if (toSearchFor.getText().equals(expression.getText()) && result == null) {
      result = expression;
    }

    super.visitCallExpression(expression);
  }


  public static PsiMethodCallExpression find(PsiMethodCallExpression typeToSearchFor, PsiFile psiFile) {
    var instance = new FindMethodCallExpressionVisitor(typeToSearchFor);
    psiFile.accept(instance);
    return instance.getResult();
  }
}
