package io.github.askmeagain.mapstructor.entities;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Output2 {

  String nested1;
  String nested2;
  String outerVariable;

  Output3 superNestedObject;

}