package io.github.askmeagain.mapstructor.entities;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.util.List;

@Data
@Builder
public class MapperConfig {

  @With
  String mapperName;
  List<String> singleFile;
  String instanceVariableName;

}
