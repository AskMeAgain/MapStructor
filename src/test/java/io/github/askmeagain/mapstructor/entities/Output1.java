package io.github.askmeagain.mapstructor.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

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

  public String getInput1() {
    return this.input1;
  }

  public String getInput2() {
    return this.input2;
  }

  public String getInput3() {
    return this.input3;
  }

  public Input1 getInput4() {
    return this.input4;
  }

  public String getInput5() {
    return this.input5;
  }

  public Output2 getNestedThings() {
    return this.nestedThings;
  }

  public void setInput1(String input1) {
    this.input1 = input1;
  }

  public void setInput2(String input2) {
    this.input2 = input2;
  }

  public void setInput3(String input3) {
    this.input3 = input3;
  }

  public void setInput4(Input1 input4) {
    this.input4 = input4;
  }

  public void setInput5(String input5) {
    this.input5 = input5;
  }

  public void setNestedThings(Output2 nestedThings) {
    this.nestedThings = nestedThings;
  }
}