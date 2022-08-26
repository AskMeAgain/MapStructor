package io.github.askmeagain.mapstructor.services;

import com.esotericsoftware.minlog.Log;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.impl.PsiJavaParserFacadeImpl;
import groovy.util.logging.Slf4j;
import io.github.askmeagain.mapstructor.entities.Mapping;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class MapstructorAction extends AnAction {

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    var editor = e.getRequiredData(CommonDataKeys.EDITOR);
    var project = e.getRequiredData(CommonDataKeys.PROJECT);

    var psiJavaParserFacade = new PsiJavaParserFacadeImpl(project);

    editor.getCaretModel().runForEachCaret(caret -> {

      var selectedText = caret.getSelectedText();

      var method = "{" + selectedText + "}";

      var codeBlock = psiJavaParserFacade.createCodeBlockFromText(method, null);

      var result = Extractor.extract("", Mapping.of(codeBlock));

      var finalMapping = extracted(result);

      for (var entry : finalMapping.entrySet()) {
        for (var mapping : entry.getValue()) {
          System.out.println(entry.getKey() + " -> " + (mapping.isLombok() ? "lombok" : mapping.getFrom().getText()));
        }
      }
    });

  }

  private static Map<String, List<Mapping>> extracted(Map<String, List<Mapping>> input) {

    var newList = new HashMap<String, List<Mapping>>();
    var nothingDone = true;

    for (var item : input.entrySet()) {
      for (var mapping : item.getValue()) {
        if (mapping.isDone()) {
          newList.putIfAbsent(item.getKey(), new ArrayList<>());
          newList.get(item.getKey()).add(mapping);
          continue;
        }

        nothingDone = false;

        var extract = Extractor.extract(item.getKey(), mapping);

        newList = mergeLists(newList, extract);
      }
    }

    if (nothingDone) {
      return newList;
    }

    return extracted(newList);
  }

  private static HashMap<String, List<Mapping>> mergeLists(
      HashMap<String, List<Mapping>> left,
      HashMap<String, List<Mapping>> right
  ) {
    for (var entry : right.entrySet()) {
      left.putIfAbsent(entry.getKey(), new ArrayList<>());
      for (var mapping : entry.getValue()) {
        left.get(entry.getKey()).add(mapping);
      }
    }
    return left;
  }
}
