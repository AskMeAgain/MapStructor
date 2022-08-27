package io.github.askmeagain.mapstructor.printer;

import io.github.askmeagain.mapstructor.entities.MappingMethods;

import java.util.List;

public class MapstructMapperPrinter {

  public static String MAPSTRUCT_TEMPLATE = "@Mapper\n" +
      "public interface $MAPPER_NAME {\n" +
      "\n" +
      "$MAPPING_METHODS" +
      "\n" +
      "}";

  public String returnMapstructTemplate(String mapperName, List<MappingMethods> mappingList) {
    return MAPSTRUCT_TEMPLATE
        .replace("$MAPPER_NAME", mapperName)
        .replace("$MAPPING_METHODS", MapstructMethodPrinter.print(mappingList));
  }
}
