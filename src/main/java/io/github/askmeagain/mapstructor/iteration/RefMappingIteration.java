package io.github.askmeagain.mapstructor.iteration;

import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.entities.MapstructMethodEntity;
import io.github.askmeagain.mapstructor.services.MapstructorUtils;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RefMappingIteration implements Iteration {

  @Override
  public void accept(MapStructMapperEntity entity) {
    for (var method : entity.getMappings()) {
      var newMappings = method.getMappings().stream()
          .map(this::calcRefTarget)
          .collect(Collectors.toList());

      method.setMappings(newMappings);
    }

    for (var method : entity.getMappings()) {
      for (var mapping : method.getMappings()) {
        for (var outputType : entity.getMappings()) {
          if (outputType.getOutputType().equals(mapping.getRefTargetType())) {
            mapping.setRefToOtherMapping(outputType);
            break;
          }
        }
      }
    }
  }

  private MapstructMethodEntity.TargetSourceContainer calcRefTarget(MapstructMethodEntity.TargetSourceContainer sourceContainer) {

    var element = sourceContainer.getSource();

    var ref = PsiTreeUtil.getChildOfType(element, PsiReferenceExpression.class);

    if (ref != null) {
      var type = MapstructorUtils.resolveBuilder(ref.getType(), ref.getProject());
      var resolvedType = MapstructorUtils.resolveCapture(type);
      return sourceContainer.withRefTargetType(resolvedType);
    }

    var methodCallExpression = PsiTreeUtil.getChildOfType(element, PsiMethodCallExpression.class);
    if (methodCallExpression != null) {
      var type = methodCallExpression.getType();
      var resolvedType = MapstructorUtils.resolveCapture(type);
      return sourceContainer.withRefTargetType(resolvedType);
    }

    return sourceContainer;
  }

}
