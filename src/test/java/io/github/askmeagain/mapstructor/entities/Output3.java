package io.github.askmeagain.mapstructor.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Output3 {

  String superNested1;
  String superNested2;
  String superOuterVariable;

}