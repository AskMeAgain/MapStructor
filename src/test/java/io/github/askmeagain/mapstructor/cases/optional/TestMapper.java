package io.github.askmeagain.mapstructor.cases.optional;

import io.github.askmeagain.mapstructor.entities.Optional;
import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import io.github.askmeagain.mapstructor.entities.Output3;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TestMapper {

  TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

  @Mapping(target = "nestedThings", source = "optObj")
  Output1 mapOutput1(Optional<Output3> optObj);

  @Mapping(target = "nested2", constant = "1")
  @Mapping(target = "superNestedObject", source = "optObj", qualifiedByName = "mapSuperNestedObject")
  Output2 mapOutput2(Optional<Output3> optObj);

  @Named("mapSuperNestedObject")
  default Output3 mapSuperNestedObject(Optional<Output3> optObj) {
    return optObj.orElse(new Output3());
  }
}