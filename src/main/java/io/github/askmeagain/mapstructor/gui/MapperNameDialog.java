package io.github.askmeagain.mapstructor.gui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;
import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.entities.MapperConfig;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MapperNameDialog extends DialogWrapper {
  private JTextField mapperNameField;
  private List<JCheckBox> singleFileCheckboxList = new ArrayList<>();
  private JTextField instanceVariableNameField;
  private final AnActionEvent e;
  private final MapStructMapperEntity mapStructMapperEntity;

  public MapperConfig getConfig() {
    return MapperConfig.builder()
        .mapperName(mapperNameField.getText())
        .singleFile(new ArrayList<>(singleFileCheckboxList.stream()
            .filter(AbstractButton::isSelected)
            .map(AbstractButton::getText)
            .collect(Collectors.toList())))
        .instanceVariableName(instanceVariableNameField.getText())
        .build();

  }

  public MapperNameDialog(AnActionEvent e, MapStructMapperEntity result) {
    super(true);

    this.e = e;
    this.mapStructMapperEntity = result;

    setTitle("Mapstructor");
    init();
  }

  @Override
  public JComponent getPreferredFocusedComponent() {
    return mapperNameField;
  }

  @Override
  public void doOKAction() {
    this.close(0);
  }

  @Override
  protected @Nullable JComponent createCenterPanel() {

    mapperNameField = new JTextField("DefaultMapper", 10);
    instanceVariableNameField = new JTextField("INSTANCE", 10);

    var builder = FormBuilder.createFormBuilder()
        .addLabeledComponent(new JBLabel("MapperName", JLabel.TRAILING), mapperNameField, 1, false)
        .addLabeledComponent(new JBLabel("Instance Variable Name", JLabel.TRAILING), instanceVariableNameField, 1, false);

    for (var method : mapStructMapperEntity.getMappings()) {
      var checkBox = new JBCheckBox(method.getOutputType().getPresentableText());
      singleFileCheckboxList.add(checkBox);
      builder = builder.addComponent(checkBox);
    }

    return builder.addComponentFillVertically(new JPanel(), 0)
        .getPanel();
  }
}
