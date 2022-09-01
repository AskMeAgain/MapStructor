package io.github.askmeagain.mapstructor.printer;

import io.github.askmeagain.mapstructor.entities.MappingMethods;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MapstructExternalMethodPrinter {

  private static final String MAPSTRUCT_METHOD = "\t@Named(\"$METHOD_NAME\")" +
      "\tdefault $OUTPUT_TYPE map$METHOD_NAME($PARAMS){\n" +
      "\treturn $METHOD_BODY;\n" +
      "\t}\n";

  public static String print(List<MappingMethods> mappings) {
    return mappings.stream()
        .map(MappingMethods::getMappings)
        .flatMap(Collection::stream)
        .filter(MappingMethods.TargetSourceContainer::isExternalMethod)
        .map(MapstructExternalMethodPrinter::psiElementToMethod)
        .collect(Collectors.joining("\n"));
  }

  private static String psiElementToMethod(MappingMethods.TargetSourceContainer container) {
    return MAPSTRUCT_METHOD
        .replace("$OUTPUT_TYPE", "OutputTODO")
        .replace("$METHOD_NAME", "output1TODO")
        .replace("$PARAMS", "StringTODO todo")
        .replace("$METHOD_BODY", container.getSource().getText());
  }

  public static String getMethodName(MappingMethods.TargetSourceContainer container) {
    return "MethodName";
  }

  public static String computeExternalMethods(MappingMethods.TargetSourceContainer container) {
    return "inputSource";
  }
}
