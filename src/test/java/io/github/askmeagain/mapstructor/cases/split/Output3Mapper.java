package io.github.askmeagain.mapstructor.cases.split;

import io.github.askmeagain.mapstructor.entities.Output3;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface Output3Mapper {

  @Mapping(target = "superNested1", constant = "1")
  @Mapping(target = "superNested2", constant = "1")
  @Mapping(target = "superOuterVariable", source = "b")
  Output3 mapOutput3(String b);
}