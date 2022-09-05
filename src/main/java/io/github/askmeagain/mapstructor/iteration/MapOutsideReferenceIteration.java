package io.github.askmeagain.mapstructor.iteration;

import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MapOutsideReferenceIteration implements Iteration {

  @Override
  public void accept(MapStructMapperEntity entity) {
    for (var method : entity.getMappings()) {
      for (var input : method.getInputs()) {
        input.setType(input.getType());
      }
    }
  }
}
