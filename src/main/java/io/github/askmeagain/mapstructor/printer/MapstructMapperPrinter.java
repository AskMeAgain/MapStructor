package io.github.askmeagain.mapstructor.printer;

import io.github.askmeagain.mapstructor.entities.CollectedResult;
import io.github.askmeagain.mapstructor.entities.MappingMethods;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class MapstructMapperPrinter {

  private static final String MAPSTRUCT_TEMPLATE = "@Mapper\n" +
      "public interface $MAPPER_NAME {\n" +
      "\n" +
      "$MAPPING_METHODS" +
      "\n" +
      "}";

  public static String returnMapstructTemplate(CollectedResult result) {
    return MAPSTRUCT_TEMPLATE
        .replace("$MAPPER_NAME", result.getMapperName())
        .replace("$MAPPING_METHODS", MapstructMethodPrinter.print(result.getMappings()));
  }
}
