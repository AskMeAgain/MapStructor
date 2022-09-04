package io.github.askmeagain.mapstructor.services;

import io.github.askmeagain.mapstructor.entities.VariableWithNameEntity;

import java.util.regex.Pattern;

public class MapstructorUtils {

  public static String printVariableWithName(VariableWithNameEntity variableWithNameEntity) {
    return variableWithNameEntity.getType().getPresentableText() + " " + variableWithNameEntity.getName().getText();
  }

  public static String toPascalCase(String str) {
    return Character.toUpperCase(str.charAt(0)) + str.substring(1);
  }

  public static boolean matchesRegex(String regex, String text) {
    var pattern = Pattern.compile(regex);
    var matcher = pattern.matcher(text);
    return matcher.find();
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
}
