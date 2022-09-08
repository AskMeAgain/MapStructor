package io.github.askmeagain.mapstructor.entities;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiType;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class MapstructExternalMethodEntity {

  PsiType outputType;
  String target;
  Set<VariableWithNameEntity> inputParams;
  PsiElement methodBody;
}
