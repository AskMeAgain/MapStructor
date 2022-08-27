package io.github.askmeagain.mapstructor.services;

import com.intellij.psi.PsiType;
import io.github.askmeagain.mapstructor.entities.Mapping;
import io.github.askmeagain.mapstructor.entities.TargetSourcecontainer;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Value
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MappingMethods {

  List<TargetSourcecontainer> mappings;

  @EqualsAndHashCode.Include
  String outputType;

}
