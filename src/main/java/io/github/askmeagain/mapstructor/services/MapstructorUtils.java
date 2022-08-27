package io.github.askmeagain.mapstructor.services;

import org.apache.commons.text.CaseUtils;

import java.util.regex.Pattern;

public class MapstructorUtils {

  public static boolean matchesRegex(String regex, String text) {
    var pattern = Pattern.compile(regex);
    var matcher = pattern.matcher(text);
    return matcher.find();
  }

  public static String extractSetterName(String expression) {

    var dotIndex = expression.indexOf(".set");
    var bracketIndex = expression.indexOf("(");

    var name = expression.substring(dotIndex + 4, bracketIndex);

    return CaseUtils.toCamelCase(name, false);
  }
}
