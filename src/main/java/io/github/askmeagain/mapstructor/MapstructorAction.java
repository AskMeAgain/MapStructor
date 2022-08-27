package io.github.askmeagain.mapstructor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.impl.PsiJavaParserFacadeImpl;
import groovy.util.logging.Slf4j;
import io.github.askmeagain.mapstructor.entities.Mapping;
import io.github.askmeagain.mapstructor.printer.MapstructPrinter;
import io.github.askmeagain.mapstructor.services.Extractor;
import io.github.askmeagain.mapstructor.services.MapstructorService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class MapstructorAction extends AnAction {

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {
    var editor = e.getRequiredData(CommonDataKeys.EDITOR);
    var project = e.getRequiredData(CommonDataKeys.PROJECT);
    var data = e.getData(PlatformDataKeys.VIRTUAL_FILE);

    var psiJavaParserFacade = new PsiJavaParserFacadeImpl(project);

    editor.getCaretModel().runForEachCaret(caret -> {

      var selectedText = caret.getSelectedText();

      var codeBlock = psiJavaParserFacade.createCodeBlockFromText("{" + selectedText + "}", null);

      var result = new MapstructorService().calc(codeBlock);

      var printResult = new MapstructPrinter().returnMapstructTemplate(
          result.getMapperName(),
          result.getOutputType(),
          Collections.emptyList()
      );

      WriteCommandAction.runWriteCommandAction(project, () -> {
        try {
          data.getParent()
              .createChildData(null, result.getMapperName() + ".java")
              .setBinaryContent(printResult.getBytes(StandardCharsets.UTF_8));
        } catch (IOException ex) {
          throw new RuntimeException("Cannot create mapper.", ex);
        }
      });
    });
  }
}
