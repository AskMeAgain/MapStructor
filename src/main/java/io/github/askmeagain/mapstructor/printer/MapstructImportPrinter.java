package io.github.askmeagain.mapstructor.printer;

import com.intellij.psi.PsiType;
import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.entities.MapstructMethodEntity;
import io.github.askmeagain.mapstructor.entities.VariableWithNameEntity;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapstructImportPrinter {

  private static final String TEMPLATE = "$NORMAL_IMPORTS$STATIC_IMPORTS";

  public static String print(MapStructMapperEntity entity) {
    var stream = Stream.of(
        "org.mapstruct.Mapper",
        "org.mapstruct.Mapping",
        "org.mapstruct.factory.Mappers"
    );

    var stringStream = Stream.of("org.mapstruct.Named")
        .filter(x -> !entity.getExternalMethodEntities().isEmpty());

    var another = entity.getMappings().stream()
        .map(MapstructMethodEntity::getOutputType)
        .map(PsiType::getCanonicalText);

    var collectedImportStream = entity.getMappings().stream()
        .map(MapstructMethodEntity::calculateDeepInputs)
        .flatMap(Collection::stream)
        .map(VariableWithNameEntity::getType)
        .map(PsiType::getCanonicalText)
        .map(MapstructImportPrinter::splitGeneric)
        .flatMap(Collection::stream);

    var normalImports = Stream.of(stream, collectedImportStream, another, stringStream)
        .flatMap(Function.identity())
        .distinct()
        .filter(x -> !x.startsWith("java.lang"))
        .filter(x -> !x.equals("String"))
        .sorted()
        .map(x -> "import " + x + ";")
        .collect(Collectors.joining("\n"));

    var staticImports = "\n\n" + entity.getStaticImports()
        .stream()
        .map(x -> "import static " + x + ";")
        .collect(Collectors.joining("\n"));

    return TEMPLATE.replace("$NORMAL_IMPORTS", normalImports)
        .replace("$STATIC_IMPORTS", entity.getStaticImports().isEmpty() ? "" : staticImports);
  }

  private static List<String> splitGeneric(String imports) {
    if (imports.contains("<")) {
      return List.of(imports.substring(0, imports.indexOf("<")), imports.substring(imports.indexOf("<") + 1, imports.length() - 1));
    }

    return List.of(imports);
  }
}
