package io.github.askmeagain.mapstructor.entities;

import com.intellij.psi.PsiElement;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TargetSourceContainer {

  PsiElement source;
  String target;
}
