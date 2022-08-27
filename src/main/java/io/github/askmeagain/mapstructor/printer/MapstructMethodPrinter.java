package io.github.askmeagain.mapstructor.printer;

import io.github.askmeagain.mapstructor.entities.MappingMethods;

import java.util.List;
import java.util.stream.Collectors;

public class MapstructMethodPrinter {

  private static final String MAPSTRUCT_MAPPING_METHOD = "$MAPPING_LIST\n" +
      "  $OUTPUT_TYPE map$OUTPUT_TYPE($PARAMS);\n";

  public static String print(List<MappingMethods> mappingList) {
    return mappingList.stream()
        .map(method -> MAPSTRUCT_MAPPING_METHOD
            .replace("$MAPPING_LIST", method.getMappings().stream()
                .map(MapstructMappingPrinter::print)
                .collect(Collectors.joining("\n")))
            .replace("$OUTPUT_TYPE", method.getOutputType().getPresentableText())
            .replace("$PARAMS", "TODOObject todoObject"))
        .collect(Collectors.joining("\n"));
  }
}
