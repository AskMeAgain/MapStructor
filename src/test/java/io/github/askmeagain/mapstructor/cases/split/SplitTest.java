package io.github.askmeagain.mapstructor.cases.split;

import io.github.askmeagain.mapstructor.common.AbstractTestBase;
import io.github.askmeagain.mapstructor.entities.MapperConfig;
import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import io.github.askmeagain.mapstructor.entities.Output3;
import lombok.SneakyThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SplitTest extends AbstractTestBase {

  @ParameterizedTest
  @CsvSource({
      "a,a",
      "a,b",
      "c,d"
  })
  @SneakyThrows
  void mappingTest(String input1, String input2) {
    var output = mappingResult(input1, input2);
    var result = TestMapper.NOT_INSTANCE.mapOutput1(input2, input1);

    assertThat(output)
        .usingRecursiveComparison()
        .isEqualTo(result);
  }

  @Override
  protected MapperConfig setMapperConfig() {
    return MapperConfig.builder()
        .mapperName("TestMapper")
        .instanceVariableName("NOT_INSTANCE")
        .singleFile(new ArrayList<>(List.of("Output2", "Output3")))
        .build();
  }

  private static Output1 mappingResult(String a, String b) {

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