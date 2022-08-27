package io.github.askmeagain.mapstructor.visitor;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.askmeagain.mapstructor.entities.BasicMapping;
import lombok.AccessLevel;
import lombok.Getter;
import io.github.askmeagain.mapstructor.services.MapstructorUtils;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static io.github.askmeagain.mapstructor.services.MapstructorUtils.extractSetterName;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MappingVisitor extends JavaRecursiveElementVisitor {

  @Getter
  private final List<BasicMapping> mappingList = new ArrayList<>();

  @Override
  public void visitMethodCallExpression(PsiMethodCallExpression expression) {
    super.visitCallExpression(expression);
    //is setter
    if (MapstructorUtils.matchesRegex(".*\\.set.*\\(.*\\)", expression.getText())) {
      var parentType = FindLastReferenceExpressionVisitor.find(expression);

      var mapping = BasicMapping.builder()
          .expression(PsiTreeUtil.findChildOfType(expression, PsiExpressionList.class))
          .parent(parentType)
          .target(extractSetterName(expression.getText()))
          .build();

      mappingList.add(mapping);
    }
  }

  public static List<BasicMapping> find(PsiElement element) {
    var visitor = new MappingVisitor();
    element.accept(visitor);
    return visitor.getMappingList();
  }
}
