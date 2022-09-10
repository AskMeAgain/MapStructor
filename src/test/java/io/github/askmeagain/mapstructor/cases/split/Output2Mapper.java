package io.github.askmeagain.mapstructor.cases.split;

import io.github.askmeagain.mapstructor.entities.Output2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface Output2Mapper extends Output3Mapper {

  @Mapping(target = "nested1", constant = "1")
  @Mapping(target = "nested2", constant = "1")
  @Mapping(target = "outerVariable", source = "a")
  @Mapping(target = "superNestedObject", source = "b")
  Output2 mapOutput2(String b, String a);

}