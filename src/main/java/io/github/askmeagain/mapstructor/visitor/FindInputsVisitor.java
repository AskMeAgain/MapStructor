package io.github.askmeagain.mapstructor.visitor;

import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiParameterImpl;
import com.intellij.psi.impl.source.tree.java.PsiLocalVariableImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class FindInputsVisitor extends JavaRecursiveElementVisitor {

  @Getter
  private final List<PsiReferenceExpression> found = new ArrayList<>();
  @Getter
  private final List<PsiParameter> lambdaParameters = new ArrayList<>();

  @Override
  public void visitLambdaExpression(PsiLambdaExpression expression) {
    super.visitLambdaExpression(expression);
    lambdaParameters.addAll(Arrays.asList(expression.getParameterList().getParameters()));
  }


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
    var found = instance.getFound();
    //remove lambda parameters

    instance.getLambdaParameters().forEach(x -> found.removeIf(y -> y.getText().equals(x.getName())));

    return found;
  }

}
