package io.github.askmeagain.mapstructor.services;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiType;
import com.intellij.psi.search.GlobalSearchScope;
import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.entities.VariableWithNameEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Pattern;

public class MapstructorUtils {

  public static String printVariableWithName(VariableWithNameEntity variableWithNameEntity) {
    return variableWithNameEntity.getType().getPresentableText() + " " + variableWithNameEntity.getName().getText();
  }

  public static String toPascalCase(String str) {
    return Character.toUpperCase(str.charAt(0)) + str.substring(1);
  }

  public static PsiType resolveBuilder(PsiType temp, Project project) {

    var presentableText = temp.getPresentableText();
    var otherText = temp.getCanonicalText();

    if (presentableText.contains("<")) {
      presentableText = presentableText.substring(0, presentableText.indexOf('<'));
      otherText = otherText.substring(0, temp.getCanonicalText().indexOf('<'));
    }

    if (!presentableText.endsWith("Builder")) {
      return temp;
    }

    var replacedName = otherText.replaceAll("." + presentableText + "$", "");
    return PsiType.getTypeByName(replacedName, project, GlobalSearchScope.allScope(project));
  }

  public static boolean matchesRegex(String regex, String text) {
    var pattern = Pattern.compile(regex);
    var matcher = pattern.matcher(text);
    return matcher.find();
  }

  public static String getMethodName(String methodName) {
    return methodName.substring(0, methodName.indexOf("("));
  }

  public static String extractSetterName(String expression) {

    var dotIndex = expression.indexOf(".set");
    var bracketIndex = expression.indexOf("(");

    var name = expression.substring(dotIndex + 4, bracketIndex);

    return Character.toLowerCase(name.charAt(0)) + name.substring(1);
  }

  public static String removeBrackets(String text) {
    if (text.startsWith("(")) {
      text = text.substring(1);
    }

    if (text.endsWith(")")) {
      text = text.substring(0, text.length() - 1);
    }

    return text;
  }

  @NotNull
  public static List<MapStructMapperEntity> splitByConfig(MapStructMapperEntity entity) {
    return List.of(entity);
  }
}
