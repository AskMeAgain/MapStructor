package io.github.askmeagain.mapstructor.visitor;

import com.intellij.psi.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FindInputsVisitor extends JavaRecursiveElementVisitor {

  private final PsiFile psiFile;

  @Getter
  private final List<PsiReferenceExpression> found = new ArrayList<>();

  @Override
  public void visitReferenceExpression(PsiReferenceExpression expression) {
    found.add(expression);
    super.visitReferenceExpression(expression);
  }

  public static List<PsiReferenceExpression> find(PsiElement element, PsiFile psiFile) {
    var instance = new FindInputsVisitor(psiFile);
    element.accept(instance);
    return instance.getFound();
  }

}
