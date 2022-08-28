package cases.case1;

public class Case1 {

  public Output1 testMethod(String test) {

    var outerVar = "a";
    var outerVar2 = "a";

    <selection>

    var output2Nested = new Output2();
    var output = new Output();
    var superNested = new Output3();

    output2Nested.setNested1("1");
    output2Nested.setNested2("1");
    output2Nested.setNested3(outerVar);

    superNested.setSuperNested1("1");
    superNested.setSuperNested2("1");
    superNested.setSuperOuterVariable(outerVar2);

    output2Nested.setSuperNestedObject(superNested);

    output.setInput1("test");
    output.setInput5("abc");

    output.setNested(output2Nested);

    return output;
    </selection>
  }

}