package io.github.askmeagain.mapstructor.entities;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
public class Output1 {

  String input1;
  String input2;
  String input3;
  Input1 input4;

  @Setter
  String input5;

}