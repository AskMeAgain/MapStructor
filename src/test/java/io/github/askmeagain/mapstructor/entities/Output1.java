package io.github.askmeagain.mapstructor.entities;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Output1 {

  String input1;
  String input2;
  String input3;
  Input1 input4;

  String input5;

  Output2 nestedThings;

}