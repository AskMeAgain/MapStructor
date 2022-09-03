package io.github.askmeagain.mapstructor.printer;

import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.entities.MapstructExternalMethodEntity;

import java.util.stream.Collectors;

import static io.github.askmeagain.mapstructor.services.MapstructorUtils.removeBrackets;
import static io.github.askmeagain.mapstructor.services.MapstructorUtils.toPascalCase;

public class MapstructExternalMethodPrinter {

  private static final String MAPSTRUCT_METHOD = "\n  @Named(\"$METHOD_NAME\")\n" +
      "  default $OUTPUT_TYPE $METHOD_NAME($PARAMS) {\n" +
      "    return $METHOD_BODY;\n" +
      "  }\n\n";

  public static String print(MapStructMapperEntity entity) {
    return entity.getExternalMethodEntities()
        .stream()
        .map(MapstructExternalMethodPrinter::psiElementToMethod)
        .collect(Collectors.joining("\n"));
  }

  private static String psiElementToMethod(MapstructExternalMethodEntity container) {
    var outputType = container.getOutputType();

    var methodName = "map" + toPascalCase(container.getTarget());

    return MAPSTRUCT_METHOD
        .replace("$OUTPUT_TYPE", outputType.getPresentableText())
        .replace("$METHOD_NAME", methodName)
        .replace("$PARAMS", container.getInputParams().stream()
            .map(x -> x.getType().getPresentableText() + " " + x.getName().getText())
            .collect(Collectors.joining(", ")))
        .replace("$METHOD_BODY", removeBrackets(container.getMethodBody().getText()));
  }
}
