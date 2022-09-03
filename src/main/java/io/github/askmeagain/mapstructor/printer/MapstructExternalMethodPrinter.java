package io.github.askmeagain.mapstructor.printer;

import com.intellij.psi.PsiElement;
import io.github.askmeagain.mapstructor.entities.MapstructMethodEntity;
import io.github.askmeagain.mapstructor.entities.VariableWithNameEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MapstructExternalMethodPrinter {

  private static final String MAPSTRUCT_METHOD = "\n\t@Named(\"$METHOD_NAME\")\n" +
      "\tdefault $OUTPUT_TYPE map$METHOD_NAME($PARAMS){\n" +
      "\t\treturn $METHOD_BODY;\n" +
      "\t}\n\n";

  public static String print(List<MapstructMethodEntity> mappings) {
    return mappings.stream()
        .map(MapstructMethodEntity::getMappings)
        .flatMap(Collection::stream)
        .filter(MapstructMethodEntity.TargetSourceContainer::isExternalMethod)
        .map(MapstructExternalMethodPrinter::psiElementToMethod)
        .collect(Collectors.joining("\n"));
  }

  private static String psiElementToMethod(MapstructMethodEntity.TargetSourceContainer container) {
    var outputType = container.getExpressionOutputType();

    return MAPSTRUCT_METHOD
        .replace("$OUTPUT_TYPE", outputType.getPresentableText())
        .replace("$METHOD_NAME", "map_" + outputType.getPresentableText())
        .replace("$PARAMS", container.getExpressionInputParameters().stream()
            .map(VariableWithNameEntity::getName)
            .map(PsiElement::getText)
            .collect(Collectors.joining(", ")))
        .replace("$METHOD_BODY", container.getSource().getText());
  }
}
