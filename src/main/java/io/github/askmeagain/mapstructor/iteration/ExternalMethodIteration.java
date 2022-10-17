package io.github.askmeagain.mapstructor.iteration;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiLocalVariable;
import com.intellij.psi.PsiMethodCallExpression;
import com.intellij.psi.PsiPolyadicExpression;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.PsiType;
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
  private final Set<PsiLocalVariable> innerMethodBodies;

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
          extracted(entity, mapping, type, method);
        }
        if (polyadicExpression != null) {
          extractPolyadicExpression(entity, mapping, polyadicExpression, method);
        }
      }
    }
  }

  private void extractPolyadicExpression(
      MapStructMapperEntity entity,
      MapstructMethodEntity.TargetSourceContainer mapping,
      PsiPolyadicExpression polyadicExpression,
      MapstructMethodEntity method
  ) {
    var type = polyadicExpression.getType();

    var refChilds = PsiTreeUtil.getChildrenOfType(polyadicExpression, PsiReferenceExpression.class);

    if (Arrays.stream(refChilds).allMatch(x -> x.getType().getCanonicalText().equals("String"))) {
      extracted(entity, mapping, refChilds[0].getType(), method);
    } else {
      extracted(entity, mapping, type, method);
    }
  }

  private void extracted(
      MapStructMapperEntity entity,
      MapstructMethodEntity.TargetSourceContainer mapping,
      PsiType type,
      MapstructMethodEntity method
  ) {
    var externalMethod = MapstructExternalMethodEntity.builder()
        .methodBody(mapping.getSource())
        .target(mapping.getTarget())
        .outputType(type)
        .attachedMethod(method)
        .inputParams(findExternalMethodInputTypes(mapping))
        .build();
    entity.getExternalMethodEntities().add(externalMethod);
    mapping.setExternalMethod(true);
    mapping.setExternalMethodEntity(externalMethod);

    //needs to be static import
    var methodRef = PsiTreeUtil.getChildOfType(mapping.getSource(), PsiMethodCallExpression.class);
    if (methodRef != null && !methodRef.getText().contains(".")) {
      var m1 = FindMethodCallExpressionVisitor.find(methodRef, psiFile);
      var parent = PsiTreeUtil.getParentOfType(m1, PsiClass.class);
      entity.getStaticImports().add(parent.getQualifiedName() + "." + getMethodName(methodRef.getText()));
    }
  }

  private Set<VariableWithNameEntity> findExternalMethodInputTypes(MapstructMethodEntity.TargetSourceContainer mapping) {
    return FindInputsVisitor.find(mapping.getSource())
        .stream()
        .map(x -> VariableWithNameEntity.builder()
            .type(x.getType())
            .name(x.getText())
            .build())
        .collect(Collectors.toSet());
  }
}
