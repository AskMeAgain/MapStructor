package io.github.askmeagain.mapstructor.iteration;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.entities.MapstructMethodEntity;
import io.github.askmeagain.mapstructor.entities.VariableWithNameEntity;

import java.util.Objects;
import java.util.stream.Collectors;

public class CalcInputIteration implements Iteration {
  @Override
  public void accept(MapStructMapperEntity entity) {
    for (var method : entity.getMappings()) {
      var collect = method.getMappings().stream()
          .map(MapstructMethodEntity.TargetSourceContainer::getSource)
          .map(this::findInputs)
          .filter(Objects::nonNull)
          .collect(Collectors.toList());

      method.getInputs().addAll(collect);
    }
  }

  private VariableWithNameEntity findInputs(PsiElement element) {

    if (PsiTreeUtil.getChildOfType(element, PsiLiteralExpression.class) != null) {
      return null; //no inputs
    }

    var psiReferenceExpression = PsiTreeUtil.getChildOfType(element, PsiReferenceExpression.class);
    if (psiReferenceExpression != null) {
      return VariableWithNameEntity.builder()
          .type(psiReferenceExpression.getType())
          .name(psiReferenceExpression)
          .build();
    }

    return null;
  }

}
