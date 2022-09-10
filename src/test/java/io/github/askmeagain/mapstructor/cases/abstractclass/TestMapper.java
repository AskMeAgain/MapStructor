package io.github.askmeagain.mapstructor.cases.abstractclass;

import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import io.github.askmeagain.mapstructor.entities.Output3;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class TestMapper {

  public static final TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

  @Mapping(target = "superNested1", constant = "1")
  @Mapping(target = "superNested2", constant = "1")
  @Mapping(target = "superOuterVariable", source = "b")
  abstract Output3 mapOutput3(String b);

  @Mapping(target = "input1", constant = "test")
  @Mapping(target = "input5", constant = "abc")
  @Mapping(target = "nestedThings", expression = "java(mapOutput2(b, a))")
  abstract Output1 mapOutput1(String b, String a);

  @Mapping(target = "nested1", constant = "1")
  @Mapping(target = "nested2", constant = "1")
  @Mapping(target = "outerVariable", source = "a")
  @Mapping(target = "superNestedObject", source = "b")
  abstract Output2 mapOutput2(String b, String a);

}