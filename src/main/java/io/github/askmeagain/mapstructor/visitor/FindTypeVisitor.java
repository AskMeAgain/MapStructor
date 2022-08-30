package io.github.askmeagain.mapstructor.visitor;

import com.intellij.psi.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FindTypeVisitor extends JavaRecursiveElementVisitor {

  @Getter
  private PsiType type;

  private final PsiReferenceExpression baseName;

  public void visitReferenceExpression(PsiReferenceExpression expression) {
    if(expression.getText().equals(baseName.getText())){
      type = expression.getType();
    }
  }


  public static PsiType find(PsiFile psiFile, PsiReferenceExpression typeToSearchFor) {
    var instance = new FindTypeVisitor(typeToSearchFor);
    psiFile.accept(instance);
    return instance.getType();
  }


}
