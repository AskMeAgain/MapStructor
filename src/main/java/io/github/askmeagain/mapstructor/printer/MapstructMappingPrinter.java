package io.github.askmeagain.mapstructor.printer;

import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.askmeagain.mapstructor.entities.MappingMethods;
import io.github.askmeagain.mapstructor.services.MapstructorUtils;
import org.apache.commons.lang.StringUtils;

import java.util.stream.Collectors;

public class MapstructMappingPrinter {

  public static String MAPSTRUCT_MAPPING_TEMPLATE = "  @Mapping(target = \"$TARGET_MAPPING\"$EXPRESSION$CONSTANT$SOURCE)";

  public static String print(MappingMethods.TargetSourceContainer mapping) {

    var constant = PsiTreeUtil.getChildOfType(mapping.getSource(), PsiLiteralExpression.class);

    var hasSource = constant == null;
    var hasConstant = constant != null;
    var hasExpression = false;

    var stripLeft = StringUtils.strip(mapping.getSource().getText(), "(");
    var stripRight = StringUtils.strip(stripLeft, ")");
    var expressionText = "";

    if (mapping.getRefToOtherMapping() != null) {
      var inputs = mapping.getRefToOtherMapping().calculateDeepInputs();

      if (inputs.size() == 1) {
        hasSource = true;
        stripRight = inputs.stream()
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

    return MAPSTRUCT_MAPPING_TEMPLATE
        .replace("$SOURCE", hasSource ? ", source = \"$SOURCE_MAPPING\"" : "")
        .replace("$EXPRESSION", hasExpression ? ", expression = \"java(" + expressionText + ")\"" : "")
        .replace("$CONSTANT", hasConstant ? ", constant = \"" + StringUtils.strip(constant.getText(), "\"") + "\"" : "")
        .replace("$TARGET_MAPPING", mapping.getTarget())
        .replace("$SOURCE_MAPPING", stripRight);
  }
}
