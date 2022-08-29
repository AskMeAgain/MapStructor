package io.github.askmeagain.mapstructor.mapper.simple;

import io.github.askmeagain.mapstructor.entities.Output1;
import io.github.askmeagain.mapstructor.entities.Output2;
import io.github.askmeagain.mapstructor.entities.Output3;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleMapperVerificationTest {

  @Test
  @SneakyThrows
  void mappingTest() {

    var outerVar = "a";
    var outerVar2 = "a";
    var output2Nested = new Output2();
    var output = new Output1();
    var superNested = new Output3();
    output2Nested.setNested1("1");
    output2Nested.setNested2("1");
    output2Nested.setOuterVariable(outerVar);
    superNested.setSuperNested1("1");
    superNested.setSuperNested2("1");
    superNested.setSuperOuterVariable(outerVar2);
    output2Nested.setSuperNestedObject(superNested);
    output.setInput1("test");
    output.setInput5("abc");
    output.setNestedThings(output2Nested);

    var original = output;

    var result = SimpleMapper.INSTANCE.mapOutput1(outerVar, outerVar2);

    assertThat(original).isEqualTo(result);

    var path1 = new File("src/test/java/io/github/askmeagain/mapstructor/mapper/simple/SimpleMapper.java");
    var path2 = new File("src/test/resources/cases/simple/expected.java");

    var inputStream1 = new FileInputStream(path1);
    var inputStream2 = new FileInputStream(path2);

    assertThat(IOUtils.contentEquals(inputStream1, inputStream2)).isTrue();

  }

}
