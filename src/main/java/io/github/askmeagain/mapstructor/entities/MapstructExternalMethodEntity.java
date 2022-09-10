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

  MapstructMethodEntity attachedMethod;
  String target;
  Set<VariableWithNameEntity> inputParams;
  PsiElement methodBody;

  @Override
  public String toString(){
    return "Method: " + outputType.getPresentableText();
  }
}

