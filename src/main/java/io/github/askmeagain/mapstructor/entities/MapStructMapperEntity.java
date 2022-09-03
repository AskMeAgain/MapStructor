package io.github.askmeagain.mapstructor.entities;

import com.intellij.psi.PsiType;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class MapStructMapperEntity {

  String packageName;
  String mapperName;
  List<MapstructMethodEntity> mappings;
  List<PsiType> imports;
  List<MapstructExternalMethodEntity> externalMethodEntities;
}
