package io.github.askmeagain.mapstructor.common;

import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public abstract class AbstractTestBase extends LightJavaCodeInsightFixtureTestCase {

  private static final String mapstructorAction = "io.github.askmeagain.mapstructor.MapstructorAction";

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

  @SneakyThrows
  private void executeTest() {

    var packageName = "src/test/java/" + this.getClass().getPackageName().replace(".", "/");
    var testFileName = packageName + "/" + this.getClass().getSimpleName() + ".java";

    var replacedMapperFile = Files.readAllLines(Path.of(testFileName)).stream()
        .map(line -> line.replace("//<selection>", "<selection>"))
        .map(line -> line.replace("//</selection>", "</selection>"))
        .collect(Collectors.joining("\n"));

    myFixture.addFileToProject(packageName + "/input.java", replacedMapperFile);
    myFixture.configureByFiles(
        packageName + "/input.java",
        packageName + "/../../entities/Input1.java",
        packageName + "/../../entities/Input2.java",
        packageName + "/../../entities/Output1.java",
        packageName + "/../../entities/Output2.java",
        packageName + "/../../entities/Output3.java"
    );

    myFixture.performEditorAction(mapstructorAction);

    //both files have same path, but are not in the same test project
    myFixture.checkResultByFile(
        packageName + "/SimpleMapper.java",
        packageName + "/SimpleMapper.java",
        false
    );
  }
}
