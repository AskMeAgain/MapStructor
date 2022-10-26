package io.github.askmeagain.mapstructor.cases.lambda2;

import io.github.askmeagain.mapstructor.entities.AnotherObject;
import io.github.askmeagain.mapstructor.entities.Optional;
import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import io.github.askmeagain.mapstructor.entities.Output3;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

//@Mapper
public interface TestMapper {

  TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

  @Mapping(target = "anotherObject", source = "optObj", qualifiedByName = "mapAnotherObject")
  Output3 mapOutput3(Optional<Output3> optObj);

  @Mapping(target = "nestedThings", source = "optObj")
  Output1 mapOutput1(Optional<Output3> optObj);

  @Mapping(target = "nested2", constant = "1")
  @Mapping(target = "superNestedObject", source = "optObj")
  Output2 mapOutput2(Optional<Output3> optObj);

  @Named("mapAnotherObject")
  default Object mapAnotherObject(Optional<Output3> optObj) {
    return optObj.map(x -> new AnotherObject(x.toString())).orElse(new AnotherObject());
  }
}