package io.github.askmeagain.mapstructor.cases.simple2;

import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SimpleMapper {

  SimpleMapper INSTANCE = Mappers.getMapper(SimpleMapper.class);

  //@Mapping(target = "input1", source = "thisIsAMethod(")
  @Mapping(target = "nestedThings", source = "abc")
  Output1 mapOutput1(String abc);

  //@Mapping(target = "nested1", source = ""1" + (2*3) + "3"")
  @Mapping(target = "nested2", source = "abc")
  Output2 mapOutput2(String abc);

}