package io.github.askmeagain.mapstructor.gui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;
import io.github.askmeagain.mapstructor.entities.MapperConfig;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MapperNameDialog extends DialogWrapper {
  private final JTextField mapperNameField = new JTextField("DefaultMapper", 10);
  private final JCheckBox singleFileCheckbox = new JBCheckBox("Split into separate files");
  private final JTextField instanceVariableNameField = new JTextField("INSTANCE", 10);

  public MapperConfig getConfig() {
    return MapperConfig.builder()
        .mapperName(mapperNameField.getText())
        .singleFile(!singleFileCheckbox.isSelected())
        .instanceVariableName(instanceVariableNameField.getText())
        .build();

  }

  public MapperNameDialog() {
    super(true);

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

    var otherPanel = FormBuilder.createFormBuilder()
        .addLabeledComponent(new JBLabel("Instance Variable Name", JLabel.TRAILING), instanceVariableNameField, 1, false)
        .addComponentFillVertically(new JPanel(), 0)
        .getPanel();

    var titledBorder = IdeBorderFactory.createTitledBorder("Other", false);
    otherPanel.setBorder(titledBorder);

    var inputPanel = FormBuilder.createFormBuilder()
        .addLabeledComponent(new JBLabel("MapperName", JLabel.TRAILING), mapperNameField, 1, false)
        .addComponent(singleFileCheckbox)
        .addComponentFillVertically(new JPanel(), 0)
        .getPanel();
    var inputPanelTitle = IdeBorderFactory.createTitledBorder("Input", false);
    inputPanel.setBorder(inputPanelTitle);

    return FormBuilder.createFormBuilder()
        .addComponent(inputPanel)
        .addComponent(otherPanel)
        .getPanel();
  }
}
