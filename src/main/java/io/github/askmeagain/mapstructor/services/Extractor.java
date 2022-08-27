package io.github.askmeagain.mapstructor.services;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.askmeagain.mapstructor.entities.Mapping;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@UtilityClass
public class Extractor {

  public static HashMap<String, List<Mapping>> expandElement(String prefix, Mapping element) {
    var newMap = new HashMap<String, List<Mapping>>();
    newMap.put(prefix, new ArrayList<>());

    for (var child : element.getFrom().getChildren()) {

      if (child instanceof PsiLocalVariable) {
        var varName = PsiTreeUtil.findChildOfType(child, PsiIdentifier.class).getText();
        var prefix1 = prefix + "." + varName;
        newMap.put(prefix1, new ArrayList<>());
        newMap.get(prefix1).add(Mapping.of(child));
      } else if (child instanceof PsiReturnStatement) {
        newMap.get(prefix).add(Mapping.of(child));
      } else if (child instanceof PsiDeclarationStatement) {
        newMap.get(prefix).add(Mapping.of(child));
      } else if (child instanceof PsiJavaToken) {
        //do nothing
      } else if (child instanceof PsiTypeElement) {
        //do nothing
      } else if (child instanceof PsiKeyword) {
        //do nothing
      } else if (child instanceof PsiWhiteSpace) {
        //do nothing
      } else if (child instanceof PsiBinaryExpression) {
        newMap.get(prefix).add(Mapping.done(child));
      } else if (child instanceof PsiReferenceExpression) {
        if(!child.getText().contains(".")){
          //newMap.get(prefix).add(Mapping.done(child));
        } else if (child.getText().contains(".builder()")) {
          newMap.get(prefix).add(Mapping.done(child).withLombok(true));
        } else {
          var varName = PsiTreeUtil.findChildOfType(child, PsiIdentifier.class).getText();
          var prefix1 = prefix + "." + varName;
          newMap.put(prefix1, new ArrayList<>());
          newMap.get(prefix1).add(Mapping.of(child));
        }
      } else if (child instanceof PsiModifierList) {
        //do nothing
      } else if (child instanceof PsiReferenceExpression) {
        newMap.get(prefix).add(Mapping.done(child));
      } else if (child instanceof PsiMethodCallExpression) {
        if (child.getText().contains(".builder()")) {
          newMap.get(prefix).add(Mapping.done(child).withLombok(true));
        } else {
          newMap.get(prefix).add(Mapping.done(child));
        }
      } else if (child instanceof PsiExpressionStatement) {
        if (child.getText().contains(".set")) {
          var varName = PsiTreeUtil.findChildOfType(child, PsiIdentifier.class).getText();
          var text = child.getText();
          var key = varName + "." + text.substring(text.indexOf(".") + 4, text.indexOf("("));
          var methodParam = PsiTreeUtil.findChildOfType(child, PsiExpressionList.class);
          newMap.put(key, new ArrayList<>());
          newMap.get(key).add(Mapping.done(methodParam));
        } else {
          throw new NotImplementedException();
        }
      }else if (child instanceof PsiNewExpression) {
        //do nothing
        int i = 0;
      }  else {
        newMap.get(prefix).add(Mapping.done(child));
      }
    }

    return newMap;
  }

}
