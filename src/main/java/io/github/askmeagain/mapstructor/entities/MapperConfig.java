package io.github.askmeagain.mapstructor.entities;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
public class MapperConfig {

  @With
  String mapperName;
  boolean singleFile;
  String instanceVariableName;

}
