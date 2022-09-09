package io.github.askmeagain.mapstructor.entities;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class MapperConfig {

  String mapperName;
  List<String> singleFile;
  String instanceVariableName;

}
