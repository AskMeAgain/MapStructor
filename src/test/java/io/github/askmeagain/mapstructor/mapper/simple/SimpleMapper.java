package io.github.askmeagain.mapstructor.mapper.simple;

import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import io.github.askmeagain.mapstructor.entities.Output3;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimpleMapper {

  SimpleMapper INSTANCE = Mappers.getMapper(SimpleMapper.class);

  @Mapping(target = "superNested1", constant = "1")
  @Mapping(target = "superNested2", constant = "1")
  @Mapping(target = "superOuterVariable", source = "outerVar2")
  Output3 mapOutput3(String outerVar2);

  @Mapping(target = "input1", constant = "test")
  @Mapping(target = "input5", constant = "abc")
  @Mapping(target = "nestedThings", expression = "java(mapOutput2(outerVar, outerVar2))")
  Output1 mapOutput1(String outerVar, String outerVar2);

  @Mapping(target = "nested1", constant = "1")
  @Mapping(target = "nested2", constant = "1")
  @Mapping(target = "outerVariable", source = "outerVar")
  @Mapping(target = "superNestedObject", source = "outerVar2")
  Output2 mapOutput2(String outerVar, String outerVar2);

}