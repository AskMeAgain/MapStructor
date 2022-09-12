package io.github.askmeagain.mapstructor.iteration;

import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.entities.MapstructMethodEntity;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MainMethodIteration implements Iteration {

  @Override
  public void accept(MapStructMapperEntity entity) {
    var solution = entity.getMappings().stream()
        .collect(Collectors.toMap(MapstructMethodEntity::getOutputType, x -> x));

    for (var method : entity.getMappings()) {
      var refMappings = method.getMappings()
          .stream()
          .filter(x -> x.getRefToOtherMapping() != null)
          .collect(Collectors.toList());

      for (var ref : refMappings) {
        solution.remove(ref.getRefTargetType());
      }
    }

    if (solution.size() == 1) {
      solution.values().stream().findFirst().orElseThrow().setMainMappingMethod(true);
    }
  }

}
