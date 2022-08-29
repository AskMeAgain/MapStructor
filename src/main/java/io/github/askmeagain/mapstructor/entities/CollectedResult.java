package io.github.askmeagain.mapstructor.entities;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CollectedResult {

  String packageName;
  String mapperName;
  List<MappingMethods> mappings;
}
