package io.github.askmeagain.mapstructor.printer;

import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.services.MapstructorUtils;

import java.util.stream.Collectors;

public class MapstructMethodPrinter {

  private static final String MAPSTRUCT_MAPPING_METHOD = "$MAPPING_LIST\n" +
      "  $ABSTRACT_METHOD$OUTPUT_TYPE map$OUTPUT_TYPE($PARAMS);\n";

  public static String print(MapStructMapperEntity entity) {
    return entity.getMappings().stream()
        .map(method -> MAPSTRUCT_MAPPING_METHOD
            .replace("$MAPPING_LIST", method.getMappings().stream()
                .sorted()
                .map(MapstructMappingPrinter::print)
                .collect(Collectors.joining("\n")))
            .replace("$ABSTRACT_METHOD", entity.getMapperConfig().isAbstractMapper() ? "abstract " : "")
            .replace("$OUTPUT_TYPE", method.getOutputType().getPresentableText())
            .replace("$PARAMS", method.calculateDeepInputs().stream()
                .map(MapstructorUtils::printVariableWithName)
                .collect(Collectors.joining(", "))))
        .collect(Collectors.joining("\n"));
  }
}
