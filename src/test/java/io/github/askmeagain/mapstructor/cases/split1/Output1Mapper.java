package io.github.askmeagain.mapstructor.cases.split1;

import io.github.askmeagain.mapstructor.entities.Output1;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Output1Mapper extends Output2Mapper {

  Output1Mapper NOT_INSTANCE = Mappers.getMapper(Output1Mapper.class);

  @Mapping(target = "input1", constant = "test")
  @Mapping(target = "input5", constant = "abc")
  @Mapping(target = "nestedThings", expression = "java(mapOutput2(b, a))")
  Output1 mapOutput1(String b, String a);

}