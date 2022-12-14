package io.github.askmeagain.mapstructor.cases.external;

import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TestMapper {

  TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

  @Mapping(target = "input1", source = "abc", qualifiedByName = "mapInput1")
  @Mapping(target = "nestedThings", source = "abc")
  Output1 mapOutput1(String abc);

  @Mapping(target = "nested1", source = "abc", qualifiedByName = "mapNested1")
  @Mapping(target = "nested2", expression = "java(ExternalMappingTest.methodWithNoInput())")
  Output2 mapOutput2(String abc);

  @Named("mapInput1")
  default String mapInput1(String abc) {
    return ExternalMappingTest.thisIsAMethod(abc + " LOL");
  }

  @Named("mapNested1")
  default String mapNested1(String abc) {
    return "1" + (2 * 3) + "3" + abc;
  }
}