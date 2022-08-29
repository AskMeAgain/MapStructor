package io.github.askmeagain.mapstructor.printer;

import com.intellij.psi.PsiType;
import io.github.askmeagain.mapstructor.entities.CollectedResult;
import io.github.askmeagain.mapstructor.entities.MappingMethods;
import io.github.askmeagain.mapstructor.entities.VariableWithName;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class MapstructMapperPrinter {

  private static final String MAPSTRUCT_TEMPLATE =
      "package $PACKAGE\n;" +
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
          "}";

  public static String returnMapstructTemplate(CollectedResult result) {

    var stream = Stream.of(
        "org.mapstruct.Mapper",
        "org.mapstruct.Mapping",
        "org.mapstruct.factory.Mappers"
    );

    var another = result.getMappings().stream()
        .map(MappingMethods::getOutputType)
        .map(PsiType::getCanonicalText);

    var collectedImportStream = result.getMappings().stream()
        .map(MappingMethods::calculateDeepInputs)
        .flatMap(Collection::stream)
        .map(VariableWithName::getType)
        .map(PsiType::getCanonicalText);

    var imports = Stream.of(stream, collectedImportStream, another)
        .flatMap(Function.identity())
        .distinct()
        .filter(x -> !x.startsWith("java.lang"))
        .sorted()
        .map(x -> "import " + x + ";")
        .collect(Collectors.joining("\n"));

    return MAPSTRUCT_TEMPLATE
        .replace("$PACKAGE", result.getPackageName())
        .replace("$IMPORTS", imports)
        .replace("$MAPPER_NAME", result.getMapperName())
        .replace("$MAPPING_METHODS", MapstructMethodPrinter.print(result.getMappings()));
  }
}
