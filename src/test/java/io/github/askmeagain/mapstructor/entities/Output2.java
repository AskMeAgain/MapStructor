package io.github.askmeagain.mapstructor.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Output2 {

  String nested1;
  String nested2;
  String outerVariable;

  Output3 superNestedObject;

  public static Output2Builder builder() {
    return new Output2Builder();
  }

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

  public static class Output2Builder {
    private String nested1;
    private String nested2;
    private String outerVariable;
    private Output3 superNestedObject;

    Output2Builder() {
    }

    public Output2Builder nested1(String nested1) {
      this.nested1 = nested1;
      return this;
    }

    public Output2Builder nested2(String nested2) {
      this.nested2 = nested2;
      return this;
    }

    public Output2Builder outerVariable(String outerVariable) {
      this.outerVariable = outerVariable;
      return this;
    }

    public Output2Builder superNestedObject(Output3 superNestedObject) {
      this.superNestedObject = superNestedObject;
      return this;
    }

    public Output2 build() {
      return new Output2(nested1, nested2, outerVariable, superNestedObject);
    }

    public String toString() {
      return "Output2.Output2Builder(nested1=" + this.nested1 + ", nested2=" + this.nested2 + ", outerVariable=" + this.outerVariable + ", superNestedObject=" + this.superNestedObject + ")";
    }
  }
}