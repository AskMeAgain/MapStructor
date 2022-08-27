package io.github.askmeagain.mapstructor.printer;

import io.github.askmeagain.mapstructor.services.MappingMethods;

import java.util.List;
import java.util.stream.Collectors;

public class MapstructPrinter {

  public static String MAPSTRUCT_TEMPLATE = "@Mapper\n" +
      "public interface $MAPPER_NAME {\n" +
      "\n" +
      "$MAPPING_METHODS" +
      "\n" +
      "}";

  public static String MAPSTRUCT_MAPPING_METHOD = "$MAPPING_LIST\n" +
      "  $OUTPUT_TYPE map$OUTPUT_TYPE();\n";

  public static String MAPSTRUCT_MAPPING_TEMPLATE = "  @Mapping(target = \"$TARGET_MAPPING\", source = \"$SOURCE_MAPPING\")";

  public String returnMapstructTemplate(String mapperName, String outputType, List<MappingMethods> mappingList) {
    return MAPSTRUCT_TEMPLATE
        .replace("$MAPPER_NAME", mapperName)
        .replace("$OUTPUT_TYPE", outputType)
        .replace("$MAPPING_METHODS", mappingList.stream()
            .map(method -> MAPSTRUCT_MAPPING_METHOD
                .replace("$MAPPING_LIST", method.getMappings().stream()
                    .map(mapping -> MAPSTRUCT_MAPPING_TEMPLATE
                        .replace("$TARGET_MAPPING", mapping.getTarget())
                        .replace("$SOURCE_MAPPING", mapping.getSource()))
                    .collect(Collectors.joining("\n")))
                .replace("$OUTPUT_TYPE", method.getOutputType()))
            .collect(Collectors.joining("\n")));
  }
}
