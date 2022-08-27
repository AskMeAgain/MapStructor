package io.github.askmeagain.mapstructor.entities;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class V2Mapping {

  PsiType parent;
  String target;
  PsiElement expression;

}
