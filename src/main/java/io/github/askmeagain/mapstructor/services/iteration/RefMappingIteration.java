package io.github.askmeagain.mapstructor.services.iteration;

import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReferenceExpression;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.entities.MapstructMethodEntity;
import io.github.askmeagain.mapstructor.services.Iteration;
import io.github.askmeagain.mapstructor.visitor.FindTypeVisitor;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RefMappingIteration implements Iteration {

  private final PsiFile psiFile;

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
      var type = FindTypeVisitor.find(psiFile, ref);
      return sourceContainer.withRefTargetType(type);
    }
    return sourceContainer;
  }

}
