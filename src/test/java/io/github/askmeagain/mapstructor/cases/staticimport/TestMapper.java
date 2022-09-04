package io.github.askmeagain.mapstructor.cases.staticimport;

import io.github.askmeagain.mapstructor.entities.Output1;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import static io.github.askmeagain.mapstructor.cases.staticimport.StaticImportTest.thisIsAStaticMethod;

@Mapper
public interface TestMapper {

  TestMapper INSTANCE = Mappers.getMapper(TestMapper.class);

  @Mapping(target = "input1", source = "abc", qualifiedByName = "mapInput1")
  @Mapping(target = "input2", source = "abc")
  Output1 mapOutput1(String abc);

  @Named("mapInput1")
  default String mapInput1(String abc) {
    return thisIsAStaticMethod(abc);
  }
}