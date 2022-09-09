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
          "public interface $MAPPER_NAME$EXTENDS {\n" +
          "\n" +
          "  $INSTANCE_METHOD" +
          "$MAPPING_METHODS" +
          "$EXTERNAL_METHODS\n" +
          "}";

  private static final String INSTANCE_TEMPLATE = "$MAPPER_NAME $INSTANCE = Mappers.getMapper($MAPPER_NAME.class);\n\n";

  public static String print(MapStructMapperEntity result) {

    var mapperConfig = result.getMapperConfig();
    var singleFileList = mapperConfig.getSingleFile();

    var joinedExternal = " extends " + singleFileList.stream()
        .map(x -> x + "Mapper")
        .collect(Collectors.joining(", "));

    return MAPSTRUCT_TEMPLATE
        .replace("$EXTENDS", singleFileList.isEmpty() ? "" : joinedExternal)
        .replace("$PACKAGE", result.getPackageName())
        .replace("$INSTANCE_METHOD", mapperConfig.getInstanceVariableName().isEmpty() ? "" : INSTANCE_TEMPLATE)
        .replace("$IMPORTS", MapstructImportPrinter.print(result))
        .replace("$INSTANCE", mapperConfig.getInstanceVariableName())
        .replace("$MAPPER_NAME", mapperConfig.getMapperName())
        .replace("$MAPPING_METHODS", MapstructMethodPrinter.print(result))
        .replace("$EXTERNAL_METHODS", MapstructExternalMethodPrinter.print(result));
  }
}
