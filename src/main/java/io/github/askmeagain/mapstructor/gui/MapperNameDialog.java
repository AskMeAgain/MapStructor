package io.github.askmeagain.mapstructor.gui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;
import io.github.askmeagain.mapstructor.entities.MapperConfig;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ItemEvent;

public class MapperNameDialog extends DialogWrapper {
  private final JTextField mapperNameField = new JTextField("DefaultMapper", 10);
  private final JCheckBox singleFileCheckbox = new JBCheckBox("Split into separate files");
  private final JCheckBox abstractMapperCheckBox = new JBCheckBox("Abstract mapper");
  private final JCheckBox replaceWithInitializeCheckbox = new JBCheckBox("Replace with mapper call");
  private final JCheckBox generateTestCheckbox = new JBCheckBox("Generate test");
  private final JTextField instanceVariableNameField = new JTextField("INSTANCE", 10);

  private String previousMapperName = "-";

  public MapperConfig getConfig() {
    return MapperConfig.builder()
        .mapperName(mapperNameField.getText())
        .singleFile(!singleFileCheckbox.isSelected())
        .replaceWithInit(replaceWithInitializeCheckbox.isSelected())
        .generateTest(generateTestCheckbox.isSelected())
        .abstractMapper(abstractMapperCheckBox.isSelected())
        .instanceVariableName(instanceVariableNameField.getText())
        .build();
  }

  public MapperNameDialog() {
    super(true);

    singleFileCheckbox.addItemListener(e -> {
      var selected = e.getStateChange() != ItemEvent.SELECTED;
      mapperNameField.setEnabled(selected);
      var temp = mapperNameField.getText();
      if (selected) {
        mapperNameField.setText(previousMapperName);
      } else {
        mapperNameField.setText(previousMapperName);
      }
      previousMapperName = temp;
    });

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
        .addComponent(abstractMapperCheckBox)
        .addComponent(replaceWithInitializeCheckbox)
        .addComponent(generateTestCheckbox)
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
