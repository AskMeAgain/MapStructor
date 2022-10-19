package io.github.askmeagain.mapstructor.cases.optional;

import io.github.askmeagain.mapstructor.common.AbstractMapperTestBase;
import io.github.askmeagain.mapstructor.entities.Optional;
import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import io.github.askmeagain.mapstructor.entities.Output3;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OptionalTest extends AbstractMapperTestBase {

  @Test
  void mappingTest() {
    var output = mappingResult(Optional.of(new Output3()));
    var result = TestMapper.INSTANCE.mapOutput1(Optional.of(new Output3()));

    assertThat(output)
        .usingRecursiveComparison()
        .isEqualTo(result);
  }

  private static Output1 mappingResult(Optional<Output3> optObj) {

    //<selection>
    var output = new Output1();
    var output2Nested = new Output2();

    output2Nested.setSuperNestedObject(optObj.orElse(new Output3()));
    output2Nested.setNested2("1");
    output.setNestedThings(output2Nested);

    return output;
    //</selection>
  }
}