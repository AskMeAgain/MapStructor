@Mapper
public interface WhatANiceMapper {

  @Mapping(target = "output.input1", constant = "test")
  @Mapping(target = "output.input5", constant = "abc")
  Output1 mapOutput1();

}