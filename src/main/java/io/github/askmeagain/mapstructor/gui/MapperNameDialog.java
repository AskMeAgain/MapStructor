package io.github.askmeagain.mapstructor.gui;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MapperNameDialog extends DialogWrapper {
    private JTextField mapperNameField;
    private final AnActionEvent e;

    public String getMapperName(){
      return mapperNameField.getText();
    }

    public MapperNameDialog(AnActionEvent e) {
      super(true);

      this.e = e;

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

      mapperNameField = new JTextField("", 10);

      return FormBuilder.createFormBuilder()
          .addLabeledComponent(new JBLabel("MapperName",JLabel.TRAILING), mapperNameField, 1, false)
          .addComponentFillVertically(new JPanel(), 0)
          .getPanel();
    }
}
