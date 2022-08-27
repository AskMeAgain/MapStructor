package io.github.askmeagain.mapstructor.entities;

import io.github.askmeagain.mapstructor.services.MappingMethods;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Result {

  String mapperName;
  String outputType;
  List<MappingMethods> mappings;
}
