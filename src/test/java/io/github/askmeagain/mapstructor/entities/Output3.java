package io.github.askmeagain.mapstructor.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Output3 {

  String superNested1;
  String superNested2;
  String superOuterVariable;

}