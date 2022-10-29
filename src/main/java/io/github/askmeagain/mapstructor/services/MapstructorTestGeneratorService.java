package io.github.askmeagain.mapstructor.services;

import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.entities.MapstructMethodEntity;
import io.github.askmeagain.mapstructor.entities.VariableWithNameEntity;
import io.github.askmeagain.mapstructor.printer.MapstructImportPrinter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.nio.charset.Charset;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MapstructorTestGeneratorService {

  private final MapStructMapperEntity mapStructMapperEntity;

  @SneakyThrows
  public String generateTest() {
    var is = getClass()
        .getClassLoader()
        .getResourceAsStream("test-template.txt");
    var template = IOUtils.toString(is, Charset.defaultCharset());

    var mainMethod = mapStructMapperEntity.getMappings()
        .stream()
        .filter(MapstructMethodEntity::isMainMappingMethod)
        .findFirst()
        .orElseThrow();

    var imports = MapstructImportPrinter.print(mapStructMapperEntity);

    return template.replace("$PACKAGE_NAME", mapStructMapperEntity.getPackageName())
        .replace("$IMPORTS", imports)
        .replace("$MAPPER_NAME", mapStructMapperEntity.getMapperConfig().getMapperName())
        .replace("$TEST_NAME", "testMap" + mainMethod.getOutputType().getPresentableText())
        .replace("$MAIN_NAME", "map" + mainMethod.getOutputType().getPresentableText())
        .replace("$PARAMS", mainMethod.getInputs()
            .stream()
            .map(VariableWithNameEntity::getName)
            .collect(Collectors.joining(", ")))
        .replace("$INIT_PARAMS", mainMethod.getInputs().stream()
            .map(x -> "var " + x.getName() + " = TODO;")
            .collect(Collectors.joining("\n")))
        .replace("$EXPECTED", "TODO");
  }
}
