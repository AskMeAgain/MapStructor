package io.github.askmeagain.mapstructor.entities;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MapstructExternalMethodEntity {

  PsiType outputType;
  String target;
  List<VariableWithNameEntity> inputParams;
  PsiElement methodBody;
}
