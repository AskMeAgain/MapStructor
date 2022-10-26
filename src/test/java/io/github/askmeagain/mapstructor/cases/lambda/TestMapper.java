package io.github.askmeagain.mapstructor.cases.lambda;

import io.github.askmeagain.mapstructor.entities.AnotherObject;
import io.github.askmeagain.mapstructor.entities.Optional;
import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import io.github.askmeagain.mapstructor.entities.Output3;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

//@Mapper
public interface TestMapper {

  TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

  @Mapping(target = "anotherObject", expression = "java(mapAnotherObject(optObj, anotherInput))")
  Output3 mapOutput3(Optional<Output3> optObj, String anotherInput);

  @Mapping(target = "nestedThings", expression = "java(mapOutput2(optObj, anotherInput))")
  Output1 mapOutput1(Optional<Output3> optObj, String anotherInput);

  @Mapping(target = "nested2", constant = "1")
  @Mapping(target = "superNestedObject", expression = "java(mapOutput3(optObj, anotherInput))")
  Output2 mapOutput2(Optional<Output3> optObj, String anotherInput);

  default Object mapAnotherObject(Optional<Output3> optObj, String anotherInput) {
    return (Object)optObj.map(x -> new AnotherObject(anotherInput)).orElse(new AnotherObject());
  }
}