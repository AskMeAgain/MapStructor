package io.github.askmeagain.mapstructor.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Output3 {

  String superNested1;
  String superNested2;
  String superOuterVariable;

  public String getSuperNested1() {
    return this.superNested1;
  }

  public String getSuperNested2() {
    return this.superNested2;
  }

  public String getSuperOuterVariable() {
    return this.superOuterVariable;
  }

  public void setSuperNested1(String superNested1) {
    this.superNested1 = superNested1;
  }

  public void setSuperNested2(String superNested2) {
    this.superNested2 = superNested2;
  }

  public void setSuperOuterVariable(String superOuterVariable) {
    this.superOuterVariable = superOuterVariable;
  }
}