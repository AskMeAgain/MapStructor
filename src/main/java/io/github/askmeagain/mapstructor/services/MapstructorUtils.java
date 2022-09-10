package io.github.askmeagain.mapstructor.services;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiCapturedWildcardType;
import com.intellij.psi.PsiType;
import com.intellij.psi.search.GlobalSearchScope;
import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import io.github.askmeagain.mapstructor.entities.MapperConfig;
import io.github.askmeagain.mapstructor.entities.MapstructMethodEntity;
import io.github.askmeagain.mapstructor.entities.VariableWithNameEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MapstructorUtils {

  public static String printVariableWithName(VariableWithNameEntity variableWithNameEntity) {
    return variableWithNameEntity.getType().getPresentableText() + " " + variableWithNameEntity.getName().getText();
  }

  public static String toPascalCase(String str) {
    return Character.toUpperCase(str.charAt(0)) + str.substring(1);
  }

  public static PsiType resolveBuilder(PsiType temp, Project project) {

    var presentableText = temp.getPresentableText();
    var otherText = temp.getCanonicalText();

    if (presentableText.contains("<")) {
      presentableText = presentableText.substring(0, presentableText.indexOf('<'));
      otherText = otherText.substring(0, temp.getCanonicalText().indexOf('<'));
    }

    if (!presentableText.endsWith("Builder")) {
      return temp;
    }

    var replacedName = otherText.replaceAll("." + presentableText + "$", "");
    return PsiType.getTypeByName(replacedName, project, GlobalSearchScope.allScope(project));
  }

  public static boolean matchesRegex(String regex, String text) {
    var pattern = Pattern.compile(regex);
    var matcher = pattern.matcher(text);
    return matcher.find();
  }

  public static String getMethodName(String methodName) {
    return methodName.substring(0, methodName.indexOf("("));
  }

  public static String extractSetterName(String expression) {

    var dotIndex = expression.indexOf(".set");
    var bracketIndex = expression.indexOf("(");

    var name = expression.substring(dotIndex + 4, bracketIndex);

    return Character.toLowerCase(name.charAt(0)) + name.substring(1);
  }

  public static String removeBrackets(String text) {
    if (text.startsWith("(")) {
      text = text.substring(1);
    }

    if (text.endsWith(")")) {
      text = text.substring(0, text.length() - 1);
    }

    return text;
  }

  public static PsiType resolveCapture(PsiType psiType) {
    if (psiType.getPresentableText().contains("capture of ?")) {
      return ((PsiCapturedWildcardType) psiType).getUpperBound();
    } else {
      return psiType;
    }
  }

  @NotNull
  public static List<MapStructMapperEntity> splitByConfig(MapStructMapperEntity entity) {

    var list = new ArrayList<MapStructMapperEntity>();

    entity.getMapperConfig()
        .getSingleFile()
        .forEach(externalFileName -> list.add(MapStructMapperEntity.builder()
            .mapperConfig(getMapperConfigPerFile(entity, externalFileName))
            .packageName(entity.getPackageName())
            .imports(entity.getImports())
            .staticImports(entity.getStaticImports())
            .mappings(entity.getMappings().stream()
                .filter(x -> x.getOutputType().getPresentableText().equals(externalFileName))
                .collect(Collectors.toList()))
            .externalMethodEntities(entity.getExternalMethodEntities())
            .build()));

    entity.getMappings().removeIf(x -> entity.getMapperConfig().getSingleFile().contains(x.getOutputType().getPresentableText()));

    list.add(entity);

    return list;
  }

  private static MapperConfig getMapperConfigPerFile(MapStructMapperEntity entity, String externalFileName) {

    var config = entity.getMapperConfig().withMapperName(externalFileName + "Mapper");

    var refMappings = entity.getMappings()
        .stream()
        .filter(x -> x.getOutputType().getPresentableText().equals(externalFileName))
        .map(MapstructMethodEntity::getMappings)
        .flatMap(Collection::stream)
        .map(MapstructMethodEntity.TargetSourceContainer::getRefToOtherMapping)
        .filter(Objects::nonNull)
        .map(x -> x.getOutputType().getPresentableText())
        .collect(Collectors.toList());

    config.setSingleFile(refMappings);

    return config;
  }
}
