package io.github.askmeagain.mapstructor.services;

import com.intellij.testFramework.fixtures.LightJavaCodeInsightFixtureTestCase;
import junit.framework.TestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MapstructorActionTest extends LightJavaCodeInsightFixtureTestCase {

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
  public void testDoStuff() {
    myFixture.configureByFiles(
        "src/test/resources/cases/case1/Case1.java",
        "src/test/java/io/github/askmeagain/mapstructor/entities/Input1.java",
        "src/test/java/io/github/askmeagain/mapstructor/entities/Output1.java"
    );

    myFixture.performEditorAction(MAPSTRUCTOR_ACTION);
  }

  @Test
  public void testSimpleStuff() {
    myFixture.configureByFiles(
        "src/test/resources/cases/simple/CaseSimple.java",
        "src/test/java/io/github/askmeagain/mapstructor/entities/Input1.java",
        "src/test/java/io/github/askmeagain/mapstructor/entities/Output1.java"
    );

    myFixture.performEditorAction(MAPSTRUCTOR_ACTION);
    myFixture.checkResultByFile(
        "src/test/resources/cases/simple/TODO.java",
        "src/test/resources/cases/simple/expected.java",
        false
    );

  }
}