package io.github.askmeagain.mapstructor.visitor;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.askmeagain.mapstructor.entities.BasicMapping;
import io.github.askmeagain.mapstructor.services.MapstructorUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static io.github.askmeagain.mapstructor.services.MapstructorUtils.extractSetterName;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MappingVisitor extends JavaRecursiveElementVisitor {

  private final int start;
  private final int end;

  @Getter
  private final List<BasicMapping> mappingList = new ArrayList<>();

  @Override
  public void visitMethodCallExpression(PsiMethodCallExpression expression) {

    if (expression.getTextOffset() < start || expression.getTextOffset() > end) {
      return;
    }

    super.visitCallExpression(expression);
    //is setter
    if (MapstructorUtils.matchesRegex(".*\\.set.*\\(.*\\)", expression.getText())) {
      var parentType = PsiTreeUtil.getChildOfType(expression.getChildren()[0], PsiReferenceExpression.class).getType();

      var mapping = BasicMapping.builder()
          .expression(PsiTreeUtil.findChildOfType(expression, PsiExpressionList.class))
          .parent(parentType)
          .target(extractSetterName(expression.getText()))
          .build();

      mappingList.add(mapping);
    }
  }

  public static List<BasicMapping> find(PsiFile psiFile, int start, int end) {
    var visitor = new MappingVisitor(start, end);
    psiFile.accept(visitor);
    return visitor.getMappingList();
  }
}
