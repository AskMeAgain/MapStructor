package io.github.askmeagain.mapstructor.entities;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@With
public class MapperConfig {

  String mapperName;
  boolean singleFile;
  boolean replaceWithInit;
  boolean abstractMapper;
  String instanceVariableName;

}
