package io.github.askmeagain.mapstructor.cases.simple;

import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import io.github.askmeagain.mapstructor.entities.Output3;

public class Case1 {

  public Output1 testMethod(String test) {

    var outerVar = "a";
    var outerVar2 = "a";

    <selection>
    var output2Nested = new Output2();
    var output = new Output1();
    var superNested = new Output3();
    output2Nested.setNested1("1");
    output2Nested.setNested2("1");
    output2Nested.setOuterVariable(outerVar);
    superNested.setSuperNested1("1");
    superNested.setSuperNested2("1");
    superNested.setSuperOuterVariable(outerVar2);
    output2Nested.setSuperNestedObject(superNested);
    output.setInput1("test");
    output.setInput5("abc");
    output.setNestedThings(output2Nested);

    return output;
    </selection>
  }

}