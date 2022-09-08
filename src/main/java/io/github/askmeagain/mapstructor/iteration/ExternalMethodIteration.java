package io.github.askmeagain.mapstructor.iteration;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.entities.MapstructExternalMethodEntity;
import io.github.askmeagain.mapstructor.entities.MapstructMethodEntity;
import io.github.askmeagain.mapstructor.entities.VariableWithNameEntity;
import io.github.askmeagain.mapstructor.visitor.FindInputsVisitor;
import io.github.askmeagain.mapstructor.visitor.FindMethodCallExpressionVisitor;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.askmeagain.mapstructor.services.MapstructorUtils.getMethodName;

@RequiredArgsConstructor
public class ExternalMethodIteration implements Iteration {

  private final PsiFile psiFile;

  @Override
  public void accept(MapStructMapperEntity entity) {
    for (var method : entity.getMappings()) {
      for (var mapping : method.getMappings()) {

        if (mapping.getRefToOtherMapping() != null) {
          continue;
        }

        var methodCallExpression = PsiTreeUtil.getChildOfType(mapping.getSource(), PsiMethodCallExpression.class);
        var polyadicExpression = PsiTreeUtil.getChildOfType(mapping.getSource(), PsiPolyadicExpression.class);

        if (methodCallExpression != null) {
          var realInstance = FindMethodCallExpressionVisitor.find(methodCallExpression, psiFile);
          var type = realInstance.getMethodExpression().getType();
          extracted(entity, mapping, type);
        }
        if (polyadicExpression != null) {
          extractPolyadicExpression(entity, mapping, polyadicExpression);
        }
      }
    }
  }

  private void extractPolyadicExpression(MapStructMapperEntity entity, MapstructMethodEntity.TargetSourceContainer mapping, PsiPolyadicExpression polyadicExpression) {
    var type = polyadicExpression.getType();

    var refChilds = PsiTreeUtil.getChildrenOfType(polyadicExpression, PsiReferenceExpression.class);

    if (Arrays.stream(refChilds).allMatch(x -> x.getType().getCanonicalText().equals("String"))) {
      extracted(entity, mapping, refChilds[0].getType());
    } else {
      extracted(entity, mapping, type);
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

  private Set<VariableWithNameEntity> findExternalMethodInputTypes(MapstructMethodEntity.TargetSourceContainer mapping) {
    return FindInputsVisitor.find(mapping.getSource())
        .stream()
        .map(x -> VariableWithNameEntity.builder()
            .type(x.getType())
            .name(x)
            .build())
        .collect(Collectors.toSet());
  }
}