package io.github.askmeagain.mapstructor.printer;

import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.askmeagain.mapstructor.entities.MapstructMethodEntity;
import io.github.askmeagain.mapstructor.services.MapstructorUtils;
import org.apache.commons.lang.StringUtils;

import java.util.stream.Collectors;

import static io.github.askmeagain.mapstructor.services.MapstructorUtils.toPascalCase;

public class MapstructMappingPrinter {

  public static String MAPSTRUCT_MAPPING_TEMPLATE = "  @Mapping(target = \"$TARGET_MAPPING\"$EXPRESSION$CONSTANT$SOURCE$QUALIFIED_BY_NAME)";

  public static String print(MapstructMethodEntity.TargetSourceContainer mapping) {

    var constant = PsiTreeUtil.getChildOfType(mapping.getSource(), PsiLiteralExpression.class);

    var hasSource = constant == null;
    var hasConstant = constant != null;
    var hasExpression = false;
    var isExternalMethod = mapping.isExternalMethod();

    var sourceText = StringUtils.strip(StringUtils.strip(mapping.getSource().getText(), "("), ")");
    var expressionText = "";

    if (mapping.getRefToOtherMapping() != null) {
      var inputs = mapping.getRefToOtherMapping().calculateDeepInputs();

      if (inputs.size() == 1) {
        hasSource = true;
        sourceText = inputs.stream()
            .map(x -> x.getName().getText())
            .collect(Collectors.joining(""));
      } else {
        hasSource = false;
        hasExpression = true;
        expressionText = "map" + mapping.getRefToOtherMapping().getOutputType().getPresentableText() + "(" + inputs.stream()
            .map(x -> x.getName().getText())
            .collect(Collectors.joining(", ")) + ")";
      }
    }

    var qualifiedText = "";
    if (isExternalMethod) {
      var methodName = "map" + toPascalCase(mapping.getTarget());

      var inputParams = mapping.getExternalMethodEntity().getInputParams();
      if (inputParams.isEmpty()) {
        hasSource = false;
        isExternalMethod = false;
        hasExpression = true;
        expressionText = MapstructorUtils.removeBrackets(mapping.getSource().getText());
      } else {
        qualifiedText = ", qualifiedByName = \"" + methodName + "\"";
        sourceText = inputParams.stream()
            .map(x -> x.getName().getText())
            .collect(Collectors.joining(", "));
      }
    }

    return MAPSTRUCT_MAPPING_TEMPLATE
        .replace("$SOURCE", hasSource ? ", source = \"$SOURCE_MAPPING\"" : "")
        .replace("$EXPRESSION", hasExpression ? ", expression = \"java(" + expressionText + ")\"" : "")
        .replace("$CONSTANT", hasConstant ? ", constant = \"" + StringUtils.strip(constant.getText(), "\"") + "\"" : "")
        .replace("$TARGET_MAPPING", mapping.getTarget())
        .replace("$SOURCE_MAPPING", sourceText)
        .replace("$QUALIFIED_BY_NAME", isExternalMethod ? qualifiedText : "");
  }
}
