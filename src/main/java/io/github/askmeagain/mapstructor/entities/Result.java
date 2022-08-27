package io.github.askmeagain.mapstructor.entities;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Result {

  String mapperName;
  String outputType;
  List<String> mappings;
}
