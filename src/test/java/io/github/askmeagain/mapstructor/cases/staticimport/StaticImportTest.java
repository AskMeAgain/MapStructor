package io.github.askmeagain.mapstructor.cases.staticimport;

import io.github.askmeagain.mapstructor.common.AbstractMapperTestBase;
import io.github.askmeagain.mapstructor.entities.Output1;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class StaticImportTest extends AbstractMapperTestBase {

  @ParameterizedTest
  @CsvSource({"abc", "def"})
  void mappingTest(String input) {
    var output = mapper(input);
    var result = TestMapper.INSTANCE.mapOutput1(input);

    assertThat(output)
        .usingRecursiveComparison()
        .isEqualTo(result);
  }

  private Output1 mapper(String abc) {
    //<selection>
    var output = new Output1();

    output.setInput1(thisIsAStaticMethod(abc));
    output.setInput2(abc);

    return output;
    //</selection>
  }

  public static String thisIsAStaticMethod(String whatANiceInput) {
    return "test" + whatANiceInput;
  }
}