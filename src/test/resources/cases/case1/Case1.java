package cases.case1;

public class Case1 {

  public Output1 testMethod(String test) {
    <selection>
    var input4 = Input1.builder()
        .input1("asd")
        .input2("asd")
      .build();
    var test3 = test.toString();
    var test2 = test + "def";

    var output = Output1.builder()
        .input1("abc")
        .input2(test2)
        .input3(test3)
        .input4(input4)
        .build();

    output.setInput5("abc")

    return output;
    </selection>
  }

}