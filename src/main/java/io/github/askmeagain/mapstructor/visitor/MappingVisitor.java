package io.github.askmeagain.mapstructor.visitor;

import com.intellij.psi.*;
import com.intellij.psi.impl.source.tree.java.PsiReferenceExpressionImpl;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.askmeagain.mapstructor.entities.BasicMapping;
import io.github.askmeagain.mapstructor.services.MapstructorUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import static io.github.askmeagain.mapstructor.services.MapstructorUtils.extractSetterName;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MappingVisitor extends JavaRecursiveElementVisitor {

  private final int start;
  private final int end;

  @Getter
  private final Set<BasicMapping> mappingList = new HashSet<>();

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
    } else if (MapstructorUtils.matchesRegex(".*\\.builder\\(\\).*", expression.getText())) {
      var parentType = ((PsiReferenceExpressionImpl) expression.getChildren()[0]).getType();
      var visitor = new LombokVisitor(parentType);
      expression.accept(visitor);
      var mappings = visitor.getMappings();
      mappingList.addAll(mappings);
    }
  }

  public static Set<BasicMapping> find(PsiFile psiFile, int start, int end) {
    var visitor = new MappingVisitor(start, end);
    psiFile.accept(visitor);
    return visitor.getMappingList();
  }
}
