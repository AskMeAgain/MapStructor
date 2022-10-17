package io.github.askmeagain.mapstructor.visitor;

import com.intellij.psi.JavaRecursiveElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLocalVariable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class FindInnerMethodBodyVisitor extends JavaRecursiveElementVisitor {

  private final int start;
  private final int end;

  @Getter
  private final Set<PsiLocalVariable> localVariables = new HashSet<>();

  public void visitLocalVariable(PsiLocalVariable variable) {

    if (variable.getTextOffset() < start || variable.getTextOffset() > end) {
      return;
    }

    super.visitLocalVariable(variable);

    localVariables.add(variable);
  }

  public static Set<PsiLocalVariable> find(PsiFile psiFile, int start, int end) {
    var visitor = new FindInnerMethodBodyVisitor(start, end);
    psiFile.accept(visitor);
    return visitor.getLocalVariables();
  }
}
