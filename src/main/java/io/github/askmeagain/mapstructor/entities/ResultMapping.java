package io.github.askmeagain.mapstructor.entities;

import com.intellij.psi.PsiType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResultMapping {

  String target;
  String source;

}
