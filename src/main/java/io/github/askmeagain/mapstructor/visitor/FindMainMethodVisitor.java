package io.github.askmeagain.mapstructor.visitor;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReturnStatement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindMainMethodVisitor extends JavaRecursiveElementVisitor {

  private final int start;
  private final int end;

  @Getter
  private boolean returnMethod;

  public void visitReturnStatement(PsiReturnStatement statement) {

    if (statement.getTextOffset() < start || statement.getTextOffset() > end) {
      return;
    }

    returnMethod = true;
    super.visitStatement(statement);
  }

  public static boolean find(PsiFile psiFile, int start, int end) {
    var instance = new FindMainMethodVisitor(start, end);
    psiFile.accept(instance);
    return instance.isReturnMethod();
  }

}
