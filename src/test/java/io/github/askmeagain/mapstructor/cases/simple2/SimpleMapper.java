package io.github.askmeagain.mapstructor.cases.simple2;

import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import static io.github.askmeagain.mapstructor.cases.simple2.MethodMappingTest.thisIsAMethod;

@Mapper
public interface SimpleMapper {

  SimpleMapper INSTANCE = Mappers.getMapper(SimpleMapper.class);

  @Mapping(target = "input1", source = "abc", qualifiedByName = "mapInput1")
  @Mapping(target = "nestedThings", source = "abc")
  Output1 mapOutput1(String abc);

  @Mapping(target = "nested1", source = "abc", qualifiedByName = "mapNested1")
  @Mapping(target = "nested2", source = "abc")
  Output2 mapOutput2(String abc);

  @Named("mapInput1")
  default String mapInput1(String abc) {
    return thisIsAMethod();
  }

  @Named("mapNested1")
  default String mapNested1(String abc) {
    return "1" + (2 * 3) + "3";
  }
}