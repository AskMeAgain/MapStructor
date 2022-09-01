package io.github.askmeagain.mapstructor.printer;

import io.github.askmeagain.mapstructor.entities.CollectedResult;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MapstructMapperPrinter {

  private static final String MAPSTRUCT_TEMPLATE =
      "package $PACKAGE;\n" +
          "\n" +
          "$IMPORTS\n" +
          "\n" +
          "@Mapper\n" +
          "public interface $MAPPER_NAME {\n" +
          "\n" +
          "  $MAPPER_NAME INSTANCE = Mappers.getMapper($MAPPER_NAME.class);\n" +
          "\n" +
          "$MAPPING_METHODS" +
          "\n" +
          "$EXTERNAL_METHODS\n" +
          "\n" +
          "}";

  public static String returnMapstructTemplate(CollectedResult result) {
    return MAPSTRUCT_TEMPLATE
        .replace("$PACKAGE", result.getPackageName())
        .replace("$IMPORTS", MapstructImportPrinter.print(result.getMappings()))
        .replace("$MAPPER_NAME", result.getMapperName())
        .replace("$MAPPING_METHODS", MapstructMethodPrinter.print(result.getMappings()))
        .replace("$EXTERNAL_METHODS", MapstructExternalMethodPrinter.print(result.getMappings()));
  }
}
