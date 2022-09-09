package io.github.askmeagain.mapstructor.common;

import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import io.github.askmeagain.mapstructor.MapstructorAction;
import io.github.askmeagain.mapstructor.entities.MapperConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.stream.Collectors;

public abstract class AbstractTestBase extends LightJavaCodeInsightFixtureTestCase {

  private static final String mapstructorAction = "io.github.askmeagain.mapstructor.MapstructorAction";

  @Override
  public final String getTestDataPath() {
    return System.getProperty("user.dir");
  }

  @Test
  public void calculateMapper() throws Exception {
    try {
      setUp();
      executeTest();
    } catch (Exception e) {
      addSuppressedException(e);
    } finally {
      super.tearDown();
    }
  }

  protected MapperConfig setMapperConfig() {
    return MapperConfig.builder()
        .instanceVariableName("INSTANCE")
        .singleFile(Collections.emptyList())
        .mapperName("TestMapper")
        .build();
  }

  @SneakyThrows
  private void executeTest() {
    MapstructorAction.DEFAULT_TEST_CONFIG = setMapperConfig();

    var packageName = "src/test/java/" + this.getClass().getPackageName().replace(".", "/");
    var testFileName = packageName + "/" + this.getClass().getSimpleName() + ".java";
    var entitiesDir = "/../../entities/";

    var replacedMapperFile = Files.readAllLines(Path.of(testFileName)).stream()
        .map(line -> line.replace("//<selection>", "<selection>"))
        .map(line -> line.replace("//</selection>", "</selection>"))
        .collect(Collectors.joining("\n"));

    myFixture.addFileToProject(packageName + "/input.java", replacedMapperFile);
    myFixture.configureByFiles(
        packageName + "/input.java",
        packageName + entitiesDir + "Input1.java",
        packageName + entitiesDir + "Input2.java",
        packageName + entitiesDir + "Output1.java",
        packageName + entitiesDir + "Output2.java",
        packageName + entitiesDir + "Output3.java"
    );

    for (var subMappers : MapstructorAction.DEFAULT_TEST_CONFIG.getSingleFile()) {
      myFixture.configureByFiles(packageName + "/" + subMappers + "Mapper.java");
    }

    myFixture.performEditorAction(mapstructorAction);

    //both files have same path, but are not in the same project
    myFixture.checkResultByFile(
        packageName + "/TestMapper.java",
        packageName + "/TestMapper.java",
        false
    );

    for (var subMappers : MapstructorAction.DEFAULT_TEST_CONFIG.getSingleFile()) {
      myFixture.checkResultByFile(
          packageName + "/" + subMappers + "Mapper.java",
          packageName + "/" + subMappers + "Mapper.java",
          false
      );
    }
  }
}
