package io.github.askmeagain.mapstructor.cases.split;

import io.github.askmeagain.mapstructor.entities.Output3;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Output3Mapper {

  Output3Mapper NOT_INSTANCE = Mappers.getMapper(Output3Mapper.class);

  @Mapping(target = "superNested1", constant = "1")
  @Mapping(target = "superNested2", constant = "1")
  @Mapping(target = "superOuterVariable", source = "b")
  Output3 mapOutput3(String b);

}