package io.github.askmeagain.mapstructor.cases.external;

import io.github.askmeagain.mapstructor.common.AbstractTestBase;
import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ExternalMappingTest extends AbstractTestBase {

  @ParameterizedTest
  @CsvSource({"abc", "def"})
  void mappingTest(String input) {
    var output = mapper(input);
    var result = SimpleMapper.INSTANCE.mapOutput1(input);

    assertThat(output)
        .usingRecursiveComparison()
        .isEqualTo(result);
  }

  private Output1 mapper(String abc) {
    //<selection>
    var output = new Output1();
    var output2 = new Output2();

    output2.setNested1("1" + (2 * 3) + "3" + abc);
    output2.setNested2(abc);

    output.setInput1(ExternalMappingTest.thisIsAMethod(abc));
    output.setNestedThings(output2);

    return output;
    //</selection>
  }

  public static String thisIsAMethod(String whatANiceInput) {
    return "test" + whatANiceInput;
  }
}