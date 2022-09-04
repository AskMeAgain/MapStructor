package io.github.askmeagain.mapstructor.printer;

import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
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
          "$EXTERNAL_METHODS\n" +
          "}";

  public static String print(MapStructMapperEntity result) {
    return MAPSTRUCT_TEMPLATE
        .replace("$PACKAGE", result.getPackageName())
        .replace("$IMPORTS", MapstructImportPrinter.print(result))
        .replace("$MAPPER_NAME", result.getMapperName())
        .replace("$MAPPING_METHODS", MapstructMethodPrinter.print(result))
        .replace("$EXTERNAL_METHODS", MapstructExternalMethodPrinter.print(result));
  }
}
