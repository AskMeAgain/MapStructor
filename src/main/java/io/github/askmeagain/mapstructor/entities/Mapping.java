package io.github.askmeagain.mapstructor.entities;

import com.intellij.psi.PsiElement;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class Mapping {

  @With
  boolean lombok;
  PsiElement from;
  boolean done;

  public static Mapping of(PsiElement element){
    return Mapping.builder()
        .from(element)
        .done(false)
        .build();
  }

  public static Mapping done(PsiElement element){
    return Mapping.builder()
        .from(element)
        .done(true)
        .build();
  }

  @Override
  public String toString(){
    return from.getText();
  }
}
