package io.github.askmeagain.mapstructor.cases.split;

import io.github.askmeagain.mapstructor.entities.Output2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Output2Mapper extends Output3Mapper {

  Output2Mapper NOT_INSTANCE = Mappers.getMapper(Output2Mapper.class);

  @Mapping(target = "nested1", constant = "1")
  @Mapping(target = "nested2", constant = "1")
  @Mapping(target = "outerVariable", source = "a")
  @Mapping(target = "superNestedObject", source = "b")
  Output2 mapOutput2(String b, String a);

}