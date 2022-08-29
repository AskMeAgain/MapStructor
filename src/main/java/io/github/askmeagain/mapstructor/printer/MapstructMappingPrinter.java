package io.github.askmeagain.mapstructor.printer;

import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.askmeagain.mapstructor.entities.MappingMethods;
import org.apache.commons.lang.StringUtils;

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
        stripRight = "TODO, BUT SINGLE MAPPING!";
      } else {
        hasExpression = true;
        expressionText = "TODO, BUT " + inputs.size() + " MAPPING!";
      }
    }

    return MAPSTRUCT_MAPPING_TEMPLATE
        .replace("$SOURCE", hasSource ? ", source = \"$SOURCE_MAPPING\"" : "")
        .replace("$EXPRESSION", hasExpression ? ", expression = \"" + expressionText + "\"" : "")
        .replace("$CONSTANT", hasConstant ? ", constant = \"" + StringUtils.strip(constant.getText(), "\"") + "\"" : "")
        .replace("$TARGET_MAPPING", mapping.getTarget())
        .replace("$SOURCE_MAPPING", stripRight);
  }
}
