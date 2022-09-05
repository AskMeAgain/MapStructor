package io.github.askmeagain.mapstructor.iteration;

import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.entities.MapstructMethodEntity;
import io.github.askmeagain.mapstructor.entities.VariableWithNameEntity;
import io.github.askmeagain.mapstructor.visitor.FindInputsVisitor;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CalcInputIteration implements Iteration {

  @Override
  public void accept(MapStructMapperEntity entity) {
    for (var method : entity.getMappings()) {
      var collect = method.getMappings().stream()
          .map(MapstructMethodEntity.TargetSourceContainer::getSource)
          .map(FindInputsVisitor::find)
          .filter(Objects::nonNull)
          .flatMap(Collection::stream)
          .map(x -> VariableWithNameEntity.builder()
              .name(x)
              .type(x.getType())
              .build())
          .collect(Collectors.toList());
      method.getInputs().addAll(collect);
    }
  }

}
