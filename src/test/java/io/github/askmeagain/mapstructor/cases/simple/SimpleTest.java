package io.github.askmeagain.mapstructor.cases.simple;

import io.github.askmeagain.mapstructor.common.AbstractTestBase;
import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import io.github.askmeagain.mapstructor.entities.Output3;
import lombok.SneakyThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleTest extends AbstractTestBase {

  @ParameterizedTest
  @CsvSource({
      "a,a",
      "a,b",
      "c,d"
  })
  @SneakyThrows
  void mappingTest(String input1, String input2) {
    var output = mappingResult(input1, input2);
    var result = TestMapper.INSTANCE.mapOutput1(input1, input2);

    assertThat(output)
        .usingRecursiveComparison()
        .isEqualTo(result);
  }

  private static Output1 mappingResult(String b, String a) {

    //<selection>
    var output = new Output1();
    var output2Nested = new Output2();
    var superNested = new Output3();

    output2Nested.setNested1("1");
    output2Nested.setNested2("1");
    output2Nested.setOuterVariable(a);
    superNested.setSuperNested1("1");
    superNested.setSuperNested2("1");
    superNested.setSuperOuterVariable(b);
    output2Nested.setSuperNestedObject(superNested);
    output.setInput1("test");
    output.setInput5("abc");
    output.setNestedThings(output2Nested);

    return output;
    //</selection>
  }
}