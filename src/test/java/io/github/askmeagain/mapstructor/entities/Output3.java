package io.github.askmeagain.mapstructor.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Output3 {

  String superNested1;
  String superNested2;
  String superOuterVariable;

  public static Output3Builder builder() {
    return new Output3Builder();
  }

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

  public static class Output3Builder {
    private String superNested1;
    private String superNested2;
    private String superOuterVariable;

    Output3Builder() {
    }

    public Output3Builder superNested1(String superNested1) {
      this.superNested1 = superNested1;
      return this;
    }

    public Output3Builder superNested2(String superNested2) {
      this.superNested2 = superNested2;
      return this;
    }

    public Output3Builder superOuterVariable(String superOuterVariable) {
      this.superOuterVariable = superOuterVariable;
      return this;
    }

    public Output3 build() {
      return new Output3(superNested1, superNested2, superOuterVariable);
    }

    public String toString() {
      return "Output3.Output3Builder(superNested1=" + this.superNested1 + ", superNested2=" + this.superNested2 + ", superOuterVariable=" + this.superOuterVariable + ")";
    }
  }
}