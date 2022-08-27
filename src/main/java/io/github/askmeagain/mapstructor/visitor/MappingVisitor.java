package io.github.askmeagain.mapstructor.visitor;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.askmeagain.mapstructor.entities.V2Mapping;
import lombok.AccessLevel;
import lombok.Getter;
import io.github.askmeagain.mapstructor.services.Utils;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static io.github.askmeagain.mapstructor.services.Utils.extractSetterName;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MappingVisitor extends JavaRecursiveElementVisitor {

  @Getter
  private final List<V2Mapping> mappingList = new ArrayList<>();

  @Override
  public void visitMethodCallExpression(PsiMethodCallExpression expression) {
    super.visitCallExpression(expression);
    //is setter
    if (Utils.matchesRegex(".*\\.set.*\\(.*\\)", expression.getText())) {
      var parentType = FindLastReferenceExpressionVisitor.find(expression);

      var mapping = V2Mapping.builder()
          .expression(PsiTreeUtil.findChildOfType(expression, PsiExpressionList.class))
          .parent(parentType)
          .target(extractSetterName(expression.getText()))
          .build();

      mappingList.add(mapping);
    }
  }

  public static List<V2Mapping> find(PsiElement element) {
    var visitor = new MappingVisitor();
    element.accept(visitor);
    return visitor.getMappingList();
  }
}
