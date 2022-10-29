package io.github.askmeagain.mapstructor.cases.lambda;

import io.github.askmeagain.mapstructor.common.AbstractMapperTestBase;
import io.github.askmeagain.mapstructor.entities.AnotherObject;
import io.github.askmeagain.mapstructor.entities.Optional;
import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import io.github.askmeagain.mapstructor.entities.Output3;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class LambdaTest extends AbstractMapperTestBase {

  @Override
  protected List<String> removeLines() {
    return List.of("import io.github.askmeagain.mapstructor.entities.AnotherObject;");
  }

  @Override
  protected Map<String, String> replaceResult() {
    return Map.of(" AnotherObject ", " Object ");
  }


  @Test
  void mappingTest() {
    var output = mappingResult(Optional.of(new Output3()), "whatever");
    var result = TestMapper.INSTANCE.mapOutput1(Optional.of(new Output3()), "whatever");

    assertThat(output)
        .usingRecursiveComparison()
        .isEqualTo(result);
  }

  private static Output1 mappingResult(Optional<Output3> optObj, String anotherInput) {

    //<selection>
    var output = new Output1();
    var output2Nested = new Output2();
    var output3 = new Output3();

    output.setNestedThings(output2Nested);
    output2Nested.setNested2("1");
    output2Nested.setSuperNestedObject(output3);
    output3.setAnotherObject(optObj.map(x -> new AnotherObject(anotherInput)).orElse(new AnotherObject()));

    return output;
    //</selection>
  }
}