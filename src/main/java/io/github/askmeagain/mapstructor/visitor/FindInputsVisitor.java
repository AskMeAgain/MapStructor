package io.github.askmeagain.mapstructor.visitor;

import com.intellij.psi.*;
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
    if (expression.getParent() instanceof PsiMethodCallExpression) {
    } else {
      found.add(expression);
    }
  }

  public static List<PsiReferenceExpression> find(PsiElement element) {
    var instance = new FindInputsVisitor();
    element.accept(instance);
    return instance.getFound();
  }

}
