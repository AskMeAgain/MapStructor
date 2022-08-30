package io.github.askmeagain.mapstructor.common;

import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractTestBase extends LightJavaCodeInsightFixtureTestCase {

  private static final String MAPSTRUCTOR_ACTION = "io.github.askmeagain.mapstructor.MapstructorAction";

  @Override
  public final String getTestDataPath() {
    return System.getProperty("user.dir");
  }

  @BeforeEach
  void setupTest() throws Exception {
    setUp();
  }

  @AfterEach
  void teardownTest() throws Exception {
    tearDown();
  }

  @Test
  public void calculateMapper() {
    executeTest();
  }

  @Test
  public void mappingResultComparison() {
    compare();
  }


  @SneakyThrows
  private void compare() {
    var displayName = getDisplayName();

    var path1 = new File("src/test/java/io/github/askmeagain/mapstructor/cases/" + displayName + "/SimpleMapper.java");
    var path2 = new File("src/test/resources/cases/" + displayName + "/expected.java");

    var inputStream1 = new FileInputStream(path1);
    var inputStream2 = new FileInputStream(path2);

    assertThat(IOUtils.contentEquals(inputStream1, inputStream2)).isTrue();
  }

  private void executeTest() {
    var displayName = getDisplayName();

    myFixture.configureByFiles(
        "src/test/resources/cases/" + displayName + "/input.java",
        "src/test/java/io/github/askmeagain/mapstructor/entities/Input1.java",
        "src/test/java/io/github/askmeagain/mapstructor/entities/Input2.java",
        "src/test/java/io/github/askmeagain/mapstructor/entities/Output1.java",
        "src/test/java/io/github/askmeagain/mapstructor/entities/Output2.java",
        "src/test/java/io/github/askmeagain/mapstructor/entities/Output3.java"
    );

    myFixture.performEditorAction(MAPSTRUCTOR_ACTION);

    myFixture.checkResultByFile(
        "src/test/resources/cases/" + displayName + "/SimpleMapper.java",
        "src/test/resources/cases/" + displayName + "/expected.java",
        false
    );
  }

  private String getDisplayName() {
    return this.getClass().getAnnotation(MapperTest.class).value();
  }
}
