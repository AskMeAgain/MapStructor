package io.github.askmeagain.mapstructor.cases.simple;

import io.github.askmeagain.mapstructor.common.AbstractTestBase;
import io.github.askmeagain.mapstructor.common.MapperTest;
import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import io.github.askmeagain.mapstructor.entities.Output3;
import lombok.SneakyThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

@MapperTest("simple")
public class MapstructorActionTest extends AbstractTestBase {

  @ParameterizedTest
  @CsvSource({
      "a,a",
      "a,b",
      "c,d"
  })
  @SneakyThrows
  void mappingTest(String input1, String input2) {

    var output = new Output1();
    var output2Nested = new Output2();
    var superNested = new Output3();

    output2Nested.setNested1("1");
    output2Nested.setNested2("1");
    output2Nested.setOuterVariable(input1);
    superNested.setSuperNested1("1");
    superNested.setSuperNested2("1");
    superNested.setSuperOuterVariable(input2);
    output2Nested.setSuperNestedObject(superNested);
    output.setInput1("test");
    output.setInput5("abc");
    output.setNestedThings(output2Nested);

    var result = SimpleMapper.INSTANCE.mapOutput1(input1, input2);

    assertThat(output)
        .usingRecursiveComparison()
        .isEqualTo(result);

  }
}