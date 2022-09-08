package io.github.askmeagain.mapstructor.cases.builder;

import io.github.askmeagain.mapstructor.common.AbstractTestBase;
import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import io.github.askmeagain.mapstructor.entities.Output3;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class BuilderMappingTest extends AbstractTestBase {

  @ParameterizedTest
  @CsvSource({"abc,def", "def,zyx"})
  void builderTest(String input1, String input2) {
    var output = mapper(input1, input2);
    var result = TestMapper.INSTANCE.mapOutput1(input1, input2);

    assertThat(result)
        .usingRecursiveComparison()
        .isEqualTo(output);
  }

  private Output1 mapper(String abc, String anotherParam) {
    //<selection>

    var testVariable = anotherParam;

    var output = Output1.builder()
        .input1("abc")
        .input2(abc)
        .nestedThings(Output2.builder()
            .nested1(abc)
            .nested2("def" + abc + "__" + (2 * 10))
            .superNestedObject(Output3.builder()
                .superNested1(abc + abc + abc + abc)
                .superNested2(testVariable)
                .build())
            .build())
        .build();

    return output;
    //</selection>
  }
}