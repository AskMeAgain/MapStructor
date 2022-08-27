package io.github.askmeagain.mapstructor.printer;

import io.github.askmeagain.mapstructor.entities.Mapping;

import java.util.List;
import java.util.stream.Collectors;

public class MapstructPrinter {

  public static String MAPSTRUCT_TEMPLATE = "@Mapper\n" +
      "public interface $MAPPER_NAME {\n" +
      "\n" +
      "  $MAPPING_LIST" +
      "  $OUTPUT_TYPE map$OUTPUT_TYPE();\n" +
      "\n" +
      "}";

  public static String MAPSTRUCT_MAPPING_TEMPLATE = "@Mapping(target = \"$TARGET_MAPPING\", constant = \"$CONST\")\n";

  public String returnMapstructTemplate(String mapperName, String outputType, List<Mapping> mappingList) {
    return MAPSTRUCT_TEMPLATE
        .replace("$MAPPER_NAME", mapperName)
        .replace("$OUTPUT_TYPE", outputType)
        .replace("$MAPPING_LIST", mappingList.stream()
            .map(mapping -> MAPSTRUCT_MAPPING_TEMPLATE
                .replace("$TARGET_MAPPING", "abc")
                .replace("$CONST", "abc")
            )
            .collect(Collectors.joining("\n")));
  }
}
