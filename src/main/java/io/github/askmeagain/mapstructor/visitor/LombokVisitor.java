package io.github.askmeagain.mapstructor.visitor;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.askmeagain.mapstructor.entities.BasicMapping;
import io.github.askmeagain.mapstructor.services.MapstructorUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class LombokVisitor extends JavaRecursiveElementVisitor {

  private PsiType currentOutputType;

  @Getter
  private final List<BasicMapping> mappings = new ArrayList<>();

  @Override
  public void visitMethodCallExpression(PsiMethodCallExpression expression) {

    super.visitMethodCallExpression(expression);

    if (expression.getText().endsWith(".builder()")) {
      var type = expression.getType();
      currentOutputType = MapstructorUtils.resolveBuilder(type, expression.getProject());
    } else {
      try {
        var source = PsiTreeUtil.getChildOfType(expression, PsiExpressionList.class);
        var target = expression.getMethodExpression().getReferenceName();

        if (expression.getText().trim().endsWith(".build())")) {
          var type = MapstructorUtils.resolveBuilder(
              PsiTreeUtil.getChildOfType(expression, PsiReferenceExpression.class).getType(),
              expression.getProject()
          );
          mappings.add(BasicMapping.builder()
              .parent(type)
              .target(target)
              .expression(source)
              .build());
        } else if (target.endsWith("build")) {
        } else {
          var parent = MapstructorUtils.resolveBuilder(currentOutputType, expression.getProject());
          mappings.add(BasicMapping.builder()
              .expression(source)
              .parent(parent)
              .target(target)
              .build());
        }
      } catch (Exception e) {
        //throw new RuntimeException(e);
      }
    }
  }

}
