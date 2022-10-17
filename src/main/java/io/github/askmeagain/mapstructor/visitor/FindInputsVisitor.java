package io.github.askmeagain.mapstructor.visitor;

import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiParameterImpl;
import com.intellij.psi.impl.source.tree.java.PsiLocalVariableImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FindInputsVisitor extends JavaRecursiveElementVisitor {

  @Getter
  private final List<PsiReferenceExpression> found = new ArrayList<>();

  @Override
  public void visitReferenceExpression(PsiReferenceExpression expression) {
    super.visitReferenceExpression(expression);
    if (expression.getParent() instanceof PsiMethodCallExpression) {
      return;
    }

    var resolve = expression.resolve();

    var isLocalVar = resolve instanceof PsiLocalVariableImpl;
    var isParameter = resolve instanceof PsiParameterImpl;

    if (!isLocalVar && !isParameter) {
      return;
    }

    var isLambdaParameter = expression.getType().getPresentableText().contains("<lambda parameter>");
    if (isLambdaParameter) {
      return;
    }

    found.add(expression);

  }

  public static List<PsiReferenceExpression> find(PsiElement element) {
    var instance = new FindInputsVisitor();
    element.accept(instance);
    return instance.getFound();
  }

}
