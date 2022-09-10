package io.github.askmeagain.mapstructor.cases.split1;

import io.github.askmeagain.mapstructor.entities.Output3;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Output3Mapper {

  Output3Mapper NOT_INSTANCE = Mappers.getMapper(Output3Mapper.class);

  @Mapping(target = "superNested1", constant = "1")
  @Mapping(target = "superNested2", expression = "java(mapSuperNested2(b, a))")
  @Mapping(target = "superOuterVariable", source = "b")
  Output3 mapOutput3(String b, String a);

  @Named("mapSuperNested2")
  default String mapSuperNested2(String b, String a) {
    return "1" + a + b;
  }
}