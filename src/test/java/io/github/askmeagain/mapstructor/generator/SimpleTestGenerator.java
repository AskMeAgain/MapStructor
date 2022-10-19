package io.github.askmeagain.mapstructor.generator;

import io.github.askmeagain.mapstructor.common.AbstractGeneratorTestBase;
import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.services.MapstructorTestGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SimpleTestGenerator extends AbstractGeneratorTestBase {

  @Test
  void simpleTest() {
    var input = MapStructMapperEntity.builder()
        .build();

    var service = new MapstructorTestGeneratorService(input);

    var result = service.generateTest();

    Assertions.assertEquals(result, getTestResult());
  }
}
