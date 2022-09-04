package io.github.askmeagain.mapstructor.iteration;

import com.intellij.psi.PsiFile;
import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.visitor.FindTypeVisitor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MapOutsideReferenceIteration implements Iteration {

  private final PsiFile psiFile;

  @Override
  public void accept(MapStructMapperEntity entity) {
    for (var method : entity.getMappings()) {
      for (var input : method.getInputs()) {
        var type = FindTypeVisitor.find(psiFile, input.getName());
        input.setType(type);
      }
    }
  }
}
