package cases.case1;

public class Case1 {

  public Output1 testMethod(String test) {
    <selection>

    var output2Nested = new Output2();
    var output = new Output();

    output2Nested.setNested1("1");
    output2Nested.setNested2("1");

    output.setInput1("test");
    output.setInput5("abc");

    output.setNested(output2Nested);

    return output;
    </selection>
  }

}