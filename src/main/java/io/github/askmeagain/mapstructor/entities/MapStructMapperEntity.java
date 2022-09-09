package io.github.askmeagain.mapstructor.entities;

import com.intellij.psi.PsiType;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.With;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class MapStructMapperEntity {

  String packageName;
  @With
  MapperConfig mapperConfig;

  @Builder.Default
  List<String> extendsList = new ArrayList<>();

  @Singular
  List<MapstructMethodEntity> mappings;

  @Singular
  List<PsiType> imports;

  @Builder.Default
  List<String> staticImports = new ArrayList<>();

  @Builder.Default
  List<MapstructExternalMethodEntity> externalMethodEntities = new ArrayList<>();
}
