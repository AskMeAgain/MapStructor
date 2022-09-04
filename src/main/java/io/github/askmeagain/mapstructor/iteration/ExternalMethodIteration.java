package io.github.askmeagain.mapstructor.iteration;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.entities.MapstructExternalMethodEntity;
import io.github.askmeagain.mapstructor.entities.MapstructMethodEntity;
import io.github.askmeagain.mapstructor.entities.VariableWithNameEntity;
import io.github.askmeagain.mapstructor.visitor.FindInputsVisitor;
import io.github.askmeagain.mapstructor.visitor.FindMethodCallExpressionVisitor;
import io.github.askmeagain.mapstructor.visitor.FindTypeVisitor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static io.github.askmeagain.mapstructor.services.MapstructorUtils.getMethodName;

@RequiredArgsConstructor
public class ExternalMethodIteration implements Iteration {

  private final PsiFile psiFile;

  @Override
  public void accept(MapStructMapperEntity entity) {
    for (var method : entity.getMappings()) {
      for (var mapping : method.getMappings()) {
        var methodCallExpression = PsiTreeUtil.getChildOfType(mapping.getSource(), PsiMethodCallExpression.class);
        var polyadicExpression = PsiTreeUtil.getChildOfType(mapping.getSource(), PsiPolyadicExpression.class);

        if (methodCallExpression != null) {
          var realInstance = FindMethodCallExpressionVisitor.find(methodCallExpression, psiFile);
          var type = realInstance.getMethodExpression().getType();
          extracted(entity, mapping, type);
        }
        if (polyadicExpression != null) {
          var type = polyadicExpression.getType();
          extracted(entity, mapping, type);
        }
      }
    }
  }

  private void extracted(MapStructMapperEntity entity, MapstructMethodEntity.TargetSourceContainer mapping, PsiType type) {
    var externalMethod = MapstructExternalMethodEntity.builder()
        .methodBody(mapping.getSource())
        .target(mapping.getTarget())
        .outputType(type)
        .inputParams(findExternalMethodInputTypes(mapping))
        .build();
    entity.getExternalMethodEntities().add(externalMethod);
    mapping.setExternalMethod(true);
    mapping.setExternalMethodEntity(externalMethod);

    var methodRef = PsiTreeUtil.getChildOfType(mapping.getSource(), PsiMethodCallExpression.class);

    //needs to be static import
    if (methodRef != null && !methodRef.getText().contains(".")) {
      var method = FindMethodCallExpressionVisitor.find(methodRef, psiFile);
      var parent = PsiTreeUtil.getParentOfType(method, PsiClass.class);
      entity.getStaticImports().add(parent.getQualifiedName() + "." + getMethodName(methodRef.getText()));
    }
  }

  private List<VariableWithNameEntity> findExternalMethodInputTypes(MapstructMethodEntity.TargetSourceContainer mapping) {
    return FindInputsVisitor.find(mapping.getSource(), psiFile)
        .stream()
        .map(x -> VariableWithNameEntity.builder()
            .type(FindTypeVisitor.find(psiFile, x))
            .name(x)
            .build())
        .sorted()
        .collect(Collectors.toList());
  }
}
