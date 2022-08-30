package io.github.askmeagain.mapstructor.visitor;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.askmeagain.mapstructor.entities.BasicMapping;
import lombok.AccessLevel;
import lombok.Getter;
import io.github.askmeagain.mapstructor.services.MapstructorUtils;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static io.github.askmeagain.mapstructor.services.MapstructorUtils.extractSetterName;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MappingVisitor extends JavaRecursiveElementVisitor {

  private final PsiFile psiFile;
  @Getter
  private final List<BasicMapping> mappingList = new ArrayList<>();

  @Override
  public void visitMethodCallExpression(PsiMethodCallExpression expression) {
    super.visitCallExpression(expression);
    //is setter
    if (MapstructorUtils.matchesRegex(".*\\.set.*\\(.*\\)", expression.getText())) {
      var parentType = FindLastReferenceExpressionVisitor.find(expression, psiFile);

      var mapping = BasicMapping.builder()
          .expression(PsiTreeUtil.findChildOfType(expression, PsiExpressionList.class))
          .parent(parentType)
          .target(extractSetterName(expression.getText()))
          .build();

      mappingList.add(mapping);
    }
  }

  public static List<BasicMapping> find(PsiElement element, PsiFile psiFile) {
    var visitor = new MappingVisitor(psiFile);
    element.accept(visitor);
    return visitor.getMappingList();
  }
}
