package io.github.askmeagain.mapstructor.common;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.nio.charset.Charset;

public abstract class AbstractGeneratorTestBase {

  @SneakyThrows
  protected String getTestResult() {
    var testName = this.getClass().getSimpleName();
    var is = getClass()
        .getClassLoader()
        .getResourceAsStream("generator-results/" + testName + ".txt");
    return IOUtils.toString(is, Charset.defaultCharset());
  }
}
