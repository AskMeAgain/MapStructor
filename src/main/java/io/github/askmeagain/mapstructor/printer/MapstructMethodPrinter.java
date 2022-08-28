package io.github.askmeagain.mapstructor.printer;

import com.intellij.psi.PsiReferenceExpression;
import io.github.askmeagain.mapstructor.entities.MappingMethods;
import io.github.askmeagain.mapstructor.entities.VariableWithName;
import org.apache.tools.ant.types.Environment;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class MapstructMethodPrinter {

  private static final String MAPSTRUCT_MAPPING_METHOD = "$MAPPING_LIST\n" +
      "  $OUTPUT_TYPE map$OUTPUT_TYPE($PARAMS);\n";

  public static String print(List<MappingMethods> mappingList) {
    return mappingList.stream()
        .map(method -> MAPSTRUCT_MAPPING_METHOD
            .replace("$MAPPING_LIST", method.getMappings().stream()
                .map(MapstructMappingPrinter::print)
                .collect(Collectors.joining("\n")))
            .replace("$OUTPUT_TYPE", method.getOutputType().getPresentableText())
            .replace("$PARAMS", method.getInputs().stream()
                .map(MapstructMethodPrinter::getInput)
                .collect(Collectors.joining(", "))))
        .collect(Collectors.joining("\n"));
  }

  private static String getInput(VariableWithName variableWithName) {
    return variableWithName.getType().getPresentableText() + " " + variableWithName.getName().getText();
  }
}
