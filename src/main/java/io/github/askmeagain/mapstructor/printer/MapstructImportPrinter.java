package io.github.askmeagain.mapstructor.printer;

import com.intellij.psi.PsiType;
import io.github.askmeagain.mapstructor.entities.MappingMethods;
import io.github.askmeagain.mapstructor.entities.VariableWithName;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapstructImportPrinter {

  public static String print(List<MappingMethods> result){
    var stream = Stream.of(
        "org.mapstruct.Mapper",
        "org.mapstruct.Mapping",
        "org.mapstruct.factory.Mappers"
    );

    var another = result.stream()
        .map(MappingMethods::getOutputType)
        .map(PsiType::getCanonicalText);

    var collectedImportStream = result.stream()
        .map(MappingMethods::calculateDeepInputs)
        .flatMap(Collection::stream)
        .map(VariableWithName::getType)
        .map(PsiType::getCanonicalText);

    return Stream.of(stream, collectedImportStream, another)
        .flatMap(Function.identity())
        .distinct()
        .filter(x -> !x.startsWith("java.lang"))
        .filter(x -> !x.equals("String"))
        .sorted()
        .map(x -> "import " + x + ";")
        .collect(Collectors.joining("\n"));
  }
}
