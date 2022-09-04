package io.github.askmeagain.mapstructor.cases.builder;

import io.github.askmeagain.mapstructor.common.AbstractTestBase;
import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class BuilderMappingTest extends AbstractTestBase {

  @ParameterizedTest
  @CsvSource({"abc", "def"})
  void builderTest(String input) {
    var output = mapper(input);
    var result = TestMapper.INSTANCE.mapOutput1(input);

    assertThat(output)
        .usingRecursiveComparison()
        .isEqualTo(result);
  }

  private Output1 mapper(String abc) {
    //<selection>

    var output = Output1.builder()
        .input1("abc")
        .input2(abc)
        .nestedThings(Output2.builder()
            .nested1(abc)
            .nested2("def")
            .build())
        .build();

    return output;
    //</selection>
  }

  public static String thisIsAMethod(String whatANiceInput) {
    return "test" + whatANiceInput;
  }

  public static String methodWithNoInput(){
    return "abc";
  }
}