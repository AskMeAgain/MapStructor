package io.github.askmeagain.mapstructor;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.PsiElement;
import groovy.util.logging.Slf4j;
import io.github.askmeagain.mapstructor.entities.MapperConfig;
import io.github.askmeagain.mapstructor.entities.MapstructMethodEntity;
import io.github.askmeagain.mapstructor.entities.VariableWithNameEntity;
import io.github.askmeagain.mapstructor.gui.MapperNameDialog;
import io.github.askmeagain.mapstructor.printer.MapstructMapperPrinter;
import io.github.askmeagain.mapstructor.services.MapstructorService;
import io.github.askmeagain.mapstructor.services.MapstructorUtils;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class MapstructorAction extends AnAction {

  public static MapperConfig DEFAULT_TEST_CONFIG;

  @Override
  public void actionPerformed(@NotNull AnActionEvent e) {

    var editor = e.getRequiredData(CommonDataKeys.EDITOR);
    var project = e.getRequiredData(CommonDataKeys.PROJECT);
    var data = e.getData(PlatformDataKeys.VIRTUAL_FILE);
    var psiFile = e.getData(PlatformDataKeys.PSI_FILE);
    var document = editor.getDocument();

    editor.getCaretModel().runForEachCaret(caret -> {

      var selectionStart = caret.getSelectionStart();
      var selectionEnd = caret.getSelectionEnd();

      var preConfigResult = new MapstructorService(psiFile).calculate(selectionStart, selectionEnd);

      var mapperNameDialog = new MapperNameDialog();

      var mapperConfig = DEFAULT_TEST_CONFIG;
      if (mapperNameDialog.getWindow() != null) {
        mapperNameDialog.show();
        if (mapperNameDialog.getExitCode() != 0) {
          return;
        }
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
          } catch (Exception ex) {
            throw new RuntimeException("Cannot create mapper.", ex);
          }
        });
      }

      if (afterConfigResult.getMapperConfig().isReplaceWithInit()) {
        WriteCommandAction.runWriteCommandAction(project, () -> {
          document.deleteString(selectionStart, selectionEnd);

          var mainMethod = afterConfigResult.getMappings()
              .stream()
              .filter(MapstructMethodEntity::isMainMappingMethod)
              .findFirst()
              .orElseThrow();

          var mapCall = INIT_TEMPLATE.replace("$RETURN", afterConfigResult.getIncludedReturn() ? "return " : "")
              .replace("$MAPPER_NAME", afterConfigResult.getMapperConfig().getMapperName())
              .replace("$INSTANCE_NAME", afterConfigResult.getMapperConfig().getInstanceVariableName())
              .replace("$MAIN_METHOD", "map" + mainMethod.getOutputType().getPresentableText())
              .replace("$PARAMS", mainMethod.calculateDeepInputs()
                  .stream()
                  .map(VariableWithNameEntity::getName)
                  .map(PsiElement::getText)
                  .collect(Collectors.joining(", ")));

          document.insertString(selectionStart, mapCall);
        });
      }
    });
  }

  private static final String INIT_TEMPLATE = "$RETURN$MAPPER_NAME.$INSTANCE_NAME.$MAIN_METHOD($PARAMS);";
}
