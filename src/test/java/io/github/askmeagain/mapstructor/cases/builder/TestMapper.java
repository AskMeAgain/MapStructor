package io.github.askmeagain.mapstructor.cases.builder;

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

  @Mapping(target = "superNested1", source = "abc", qualifiedByName = "mapSuperNested1")
  @Mapping(target = "superNested2", source = "testVariable")
  Output3 mapOutput3(String abc, String testVariable);

  @Mapping(target = "input1", constant = "abc")
  @Mapping(target = "input2", source = "abc")
  @Mapping(target = "nestedThings", expression = "java(mapOutput2(abc, testVariable))")
  Output1 mapOutput1(String abc, String testVariable);

  @Mapping(target = "nested1", source = "abc")
  @Mapping(target = "nested2", constant = "def")
  @Mapping(target = "superNestedObject", expression = "java(mapOutput3(abc, testVariable))")
  Output2 mapOutput2(String abc, String testVariable);

  @Named("mapSuperNested1")
  default String mapSuperNested1(String abc) {
    return abc + abc + abc + abc;
  }
}