package io.github.askmeagain.mapstructor.entities;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TargetSourcecontainer {

  String source;
  String target;
}
