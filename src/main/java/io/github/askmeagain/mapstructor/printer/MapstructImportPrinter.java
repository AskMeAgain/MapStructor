package io.github.askmeagain.mapstructor.printer;

import com.intellij.psi.PsiType;
import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.entities.MapstructMethodEntity;
import io.github.askmeagain.mapstructor.entities.VariableWithNameEntity;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapstructImportPrinter {

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
        .map(PsiType::getCanonicalText);

    return Stream.of(stream, collectedImportStream, another, stringStream)
        .flatMap(Function.identity())
        .distinct()
        .filter(x -> !x.startsWith("java.lang"))
        .filter(x -> !x.equals("String"))
        .sorted()
        .map(x -> "import " + x + ";")
        .collect(Collectors.joining("\n"));
  }
}
