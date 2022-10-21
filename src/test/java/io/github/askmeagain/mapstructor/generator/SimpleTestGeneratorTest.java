package io.github.askmeagain.mapstructor.generator;

import com.intellij.psi.PsiType;
import io.github.askmeagain.mapstructor.common.AbstractGeneratorTestBase;
import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.entities.MapstructMethodEntity;
import io.github.askmeagain.mapstructor.entities.VariableWithNameEntity;
import io.github.askmeagain.mapstructor.services.MapstructorTestGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

class SimpleTestGeneratorTest extends AbstractGeneratorTestBase {

  @Test
  void simpleTest() {
    var input = MapStructMapperEntity.builder()
        .packageName("io.github.askmeagain.test.anotherTest")
        .imports(List.of(PsiType.BOOLEAN, PsiType.FLOAT))
        .staticImports(List.of(PsiType.LONG.getName()))
        .mappings(List.of(MapstructMethodEntity.builder()
            .inputs(Set.of(VariableWithNameEntity.builder()
                .type(PsiType.FLOAT)
                .name("input")
                .build()))
            .mainMappingMethod(true)
            .build()))
        .build();

    var service = new MapstructorTestGeneratorService(input);

    var result = service.generateTest();

    Assertions.assertEquals(result, getTestResult());
  }
}
