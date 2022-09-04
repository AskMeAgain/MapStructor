package io.github.askmeagain.mapstructor.entities;

import com.intellij.psi.PsiType;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class MapStructMapperEntity {

  String packageName;
  String mapperName;

  @Singular
  List<MapstructMethodEntity> mappings;

  @Singular
  List<PsiType> imports;

  @Builder.Default
  List<String> staticImports = new ArrayList<>();

  @Builder.Default
  List<MapstructExternalMethodEntity> externalMethodEntities = new ArrayList<>();
}
