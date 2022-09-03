package io.github.askmeagain.mapstructor.services;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import io.github.askmeagain.mapstructor.entities.*;
import io.github.askmeagain.mapstructor.visitor.FindInputsVisitor;
import io.github.askmeagain.mapstructor.visitor.FindMethodCallExpressionVisitor;
import io.github.askmeagain.mapstructor.visitor.FindTypeVisitor;
import io.github.askmeagain.mapstructor.visitor.MappingVisitor;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MapstructorService {

  private final PsiFile psiFile;

  public MapStructMapperEntity calculate(PsiCodeBlock codeBlock) {

    var straightMappingList = MappingVisitor.find(codeBlock, psiFile);

    var dividedByParent = straightMappingList.stream()
        .collect(Collectors.groupingBy(BasicMapping::getParent));

    var result = dividedByParent.entrySet()
        .stream()
        .map(mappingByType -> MapstructMethodEntity.builder()
            .outputType(mappingByType.getKey())
            .mappings(mappingByType.getValue()
                .stream()
                .map(mapping -> MapstructMethodEntity.TargetSourceContainer.builder()
                    .target(mapping.getTarget())
                    .source(mapping.getExpression())
                    .build())
                .collect(Collectors.toList()))
            .build())
        .collect(Collectors.toList());

    var packageName = ((PsiClassOwner) psiFile).getPackageName();

    var mapstructEntity = MapStructMapperEntity.builder()
        .mapperName("SimpleMapper")
        .packageName(packageName)
        .mappings(result)
        .build();

    calcInputs(mapstructEntity);

    findOutsideInputs(mapstructEntity);

    //we need to find reference mappings
    findRefMappings(mapstructEntity);

    findExternalMethods(mapstructEntity);

    return mapstructEntity;
  }

  private void findExternalMethods(MapStructMapperEntity entity) {

    for (var method : entity.getMappings()) {
      for (var mapping : method.getMappings()) {

        var methodCallExpression = PsiTreeUtil.getChildOfType(mapping.getSource(), PsiMethodCallExpression.class);
        if (methodCallExpression != null) {
          var realInstance = FindMethodCallExpressionVisitor.find(methodCallExpression, psiFile);
          var externalMethod = MapstructExternalMethodEntity.builder()
              .methodBody(mapping.getSource())
              .target(mapping.getTarget())
              .outputType(realInstance.getMethodExpression().getType())
              .inputParams(findExternalMethodInputTypes(mapping))
              .build();
          entity.getExternalMethodEntities().add(externalMethod);
          mapping.setExternalMethod(true);
          mapping.setExternalMethodEntity(externalMethod);
        }
      }
    }
  }

  private List<VariableWithNameEntity> findExternalMethodInputTypes(MapstructMethodEntity.TargetSourceContainer mapping) {
    return FindInputsVisitor.find(mapping.getSource(), psiFile)
        .stream()
        .map(x -> VariableWithNameEntity.builder()
            .type(FindTypeVisitor.find(psiFile, x))
            .name(x)
            .build())
        .collect(Collectors.toList());
  }

  private void findRefMappings(MapStructMapperEntity entity) {

    for (var method : entity.getMappings()) {
      var newMappings = method.getMappings().stream()
          .map(this::calcRefTarget)
          .collect(Collectors.toList());

      method.setMappings(newMappings);
    }

    for (var method : entity.getMappings()) {
      for (var mapping : method.getMappings()) {
        for (var outputType : entity.getMappings()) {
          if (outputType.getOutputType().equals(mapping.getRefTargetType())) {
            mapping.setRefToOtherMapping(outputType);
            break;
          }
        }
      }
    }
  }

  private MapstructMethodEntity.TargetSourceContainer calcRefTarget(MapstructMethodEntity.TargetSourceContainer sourceContainer) {

    var element = sourceContainer.getSource();

    var ref = PsiTreeUtil.getChildOfType(element, PsiReferenceExpression.class);

    if (ref != null) {
      var type = FindTypeVisitor.find(psiFile, ref);
      return sourceContainer.withRefTargetType(type);
    }
    return sourceContainer;
  }

  private void findOutsideInputs(MapStructMapperEntity result) {
    for (var method : result.getMappings()) {
      for (var input : method.getInputs()) {
        var type = FindTypeVisitor.find(psiFile, input.getName());
        input.setType(type);
      }
    }
  }

  private void calcInputs(MapStructMapperEntity result) {

    for (var method : result.getMappings()) {
      var collect = method.getMappings().stream()
          .map(MapstructMethodEntity.TargetSourceContainer::getSource)
          .map(this::findInputs)
          .filter(Objects::nonNull)
          .collect(Collectors.toList());

      method.getInputs().addAll(collect);
    }
  }

  private VariableWithNameEntity findInputs(PsiElement element) {

    if (PsiTreeUtil.getChildOfType(element, PsiLiteralExpression.class) != null) {
      return null; //no inputs
    }

    var psiReferenceExpression = PsiTreeUtil.getChildOfType(element, PsiReferenceExpression.class);
    if (psiReferenceExpression != null) {
      return VariableWithNameEntity.builder()
          .type(psiReferenceExpression.getType())
          .name(psiReferenceExpression)
          .build();
    }

    return null;
  }
}
