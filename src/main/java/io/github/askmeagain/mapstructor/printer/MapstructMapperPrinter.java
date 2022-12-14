package io.github.askmeagain.mapstructor.printer;

import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class MapstructMapperPrinter {

  private static final String MAPSTRUCT_TEMPLATE =
      "package $PACKAGE;\n" +
          "\n" +
          "$IMPORTS\n" +
          "\n" +
          "@Mapper\n" +
          "public $MAPPER_IMPLEMENTATION $MAPPER_NAME$EXTENDS {\n" +
          "\n" +
          "$INSTANCE_METHOD" +
          "$MAPPING_METHODS" +
          "$EXTERNAL_METHODS\n" +
          "}";

  private static final String INSTANCE_TEMPLATE =
      "  $STATIC_METHOD$MAPPER_NAME $INSTANCE = Mappers.getMapper($MAPPER_NAME.class);\n\n";

  public static String print(MapStructMapperEntity result) {

    var mapperConfig = result.getMapperConfig();

    var joinedExternal = " extends " + result.getExtendsList()
        .stream()
        .map(x -> x + "Mapper")
        .collect(Collectors.joining(", "));

    return MAPSTRUCT_TEMPLATE
        .replace("$MAPPER_IMPLEMENTATION", mapperConfig.isAbstractMapper() ? "abstract class" : "interface")
        .replace("$EXTENDS", result.getExtendsList().isEmpty() ? "" : joinedExternal)
        .replace("$PACKAGE", result.getPackageName())
        .replace("$INSTANCE_METHOD", mapperConfig.getInstanceVariableName().isEmpty() ? "" : INSTANCE_TEMPLATE)
        .replace("$IMPORTS", MapstructImportPrinter.print(result))
        .replace("$INSTANCE", mapperConfig.getInstanceVariableName())
        .replace("$MAPPER_NAME", mapperConfig.getMapperName())
        .replace("$STATIC_METHOD", mapperConfig.isAbstractMapper() ? "public static final " : "")
        .replace("$MAPPING_METHODS", MapstructMethodPrinter.print(result))
        .replace("$EXTERNAL_METHODS", MapstructExternalMethodPrinter.print(result));
  }
}
