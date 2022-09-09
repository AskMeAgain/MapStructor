package io.github.askmeagain.mapstructor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import groovy.util.logging.Slf4j;
import io.github.askmeagain.mapstructor.entities.MapperConfig;
import io.github.askmeagain.mapstructor.gui.MapperNameDialog;
import io.github.askmeagain.mapstructor.printer.MapstructMapperPrinter;
import io.github.askmeagain.mapstructor.services.MapstructorService;
import io.github.askmeagain.mapstructor.services.MapstructorUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class MapstructorAction extends AnAction {

  public static MapperConfig DEFAULT_TEST_CONFIG;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {


    var editor = e.getRequiredData(CommonDataKeys.EDITOR);
    var project = e.getRequiredData(CommonDataKeys.PROJECT);
    var data = e.getData(PlatformDataKeys.VIRTUAL_FILE);
    var psiFile = e.getData(PlatformDataKeys.PSI_FILE);

    editor.getCaretModel().runForEachCaret(caret -> {

      var preConfigResult = new MapstructorService(psiFile).calculate(caret.getSelectionStart(), caret.getSelectionEnd());

      var mapperNameDialog = new MapperNameDialog(e, preConfigResult);

      var mapperConfig = DEFAULT_TEST_CONFIG;
      if (mapperNameDialog.getWindow() != null) {
        mapperNameDialog.show();
        mapperConfig = mapperNameDialog.getConfig();
      }

      var afterConfigResult = preConfigResult.withMapperConfig(mapperConfig);

      for (var mappers : MapstructorUtils.splitByConfig(afterConfigResult)) {
        var printResult = MapstructMapperPrinter.print(mappers);

        WriteCommandAction.runWriteCommandAction(project, () -> {
          try {
            data.getParent()
                .createChildData(null, mappers.getMapperConfig().getMapperName() + ".java")
                .setBinaryContent(printResult.getBytes(StandardCharsets.UTF_8));
          } catch (IOException ex) {
            throw new RuntimeException("Cannot create mapper.", ex);
          }
        });
      }
    });
  }
}
