package io.github.askmeagain.mapstructor.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Output2 {

  String nested1;
  String nested2;
  String outerVariable;

  Output3 superNestedObject;

  public String getNested1() {
    return this.nested1;
  }

  public String getNested2() {
    return this.nested2;
  }

  public String getOuterVariable() {
    return this.outerVariable;
  }

  public Output3 getSuperNestedObject() {
    return this.superNestedObject;
  }

  public void setNested1(String nested1) {
    this.nested1 = nested1;
  }

  public void setNested2(String nested2) {
    this.nested2 = nested2;
  }

  public void setOuterVariable(String outerVariable) {
    this.outerVariable = outerVariable;
  }

  public void setSuperNestedObject(Output3 superNestedObject) {
    this.superNestedObject = superNestedObject;
  }
}