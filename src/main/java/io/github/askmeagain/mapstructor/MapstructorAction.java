package io.github.askmeagain.mapstructor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import groovy.util.logging.Slf4j;
import io.github.askmeagain.mapstructor.gui.MapperNameDialog;
import io.github.askmeagain.mapstructor.printer.MapstructMapperPrinter;
import io.github.askmeagain.mapstructor.services.MapstructorService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class MapstructorAction extends AnAction {

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {

    var mapperNameDialog = new MapperNameDialog(e);
    var mapperName = "TestMapper";
    if (mapperNameDialog.getWindow() != null) {
      mapperNameDialog.show();
      mapperName = mapperNameDialog.getMapperName();
    }

    var editor = e.getRequiredData(CommonDataKeys.EDITOR);
    var project = e.getRequiredData(CommonDataKeys.PROJECT);
    var data = e.getData(PlatformDataKeys.VIRTUAL_FILE);
    var psiFile = e.getData(PlatformDataKeys.PSI_FILE);

    String finalMapperName = mapperName;

    editor.getCaretModel().runForEachCaret(caret -> {

      var result = new MapstructorService(psiFile, finalMapperName).calculate(caret.getSelectionStart(), caret.getSelectionEnd());

      var printResult = MapstructMapperPrinter.print(result);

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
