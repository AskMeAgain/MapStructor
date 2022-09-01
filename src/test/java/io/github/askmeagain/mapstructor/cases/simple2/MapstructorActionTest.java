package io.github.askmeagain.mapstructor.cases.simple2;

import io.github.askmeagain.mapstructor.common.AbstractTestBase;
import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MapstructorActionTest extends AbstractTestBase {

  @Test
  @SneakyThrows
  void mappingTest() {
    var output = mapper("abc");
    var result = SimpleMapper.INSTANCE.mapOutput1("abc");

    assertThat(output)
        .usingRecursiveComparison()
        .isEqualTo(result);
  }

  private static Output1 mapper(String abc) {
    //<selection>
    var output = new Output1();
    var output2 = new Output2();

    output2.setNested1("1" + (2*3) + "3");
    output2.setNested2(abc);

    output.setInput1(thisIsAMethod());
    output.setNestedThings(output2);

    return output;
    //</selection>
  }

  private static String thisIsAMethod() {
    return "test";
  }
}