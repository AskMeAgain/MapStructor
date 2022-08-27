package io.github.askmeagain.mapstructor.services;

import com.intellij.psi.PsiCodeBlock;
import com.intellij.psi.PsiType;
import io.github.askmeagain.mapstructor.entities.Mapping;
import io.github.askmeagain.mapstructor.entities.Result;
import io.github.askmeagain.mapstructor.entities.ResultMapping;
import io.github.askmeagain.mapstructor.entities.TargetSourcecontainer;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class MapstructorService {

  public Result calc(PsiCodeBlock codeBlock) {
    var result = Extractor.expandElement("", Mapping.of(codeBlock));

    var finalMapping = extracted(result);

    var methodList = finalMapping.entrySet().stream()
        .map(x -> ResultMapping.builder()
            .target(x.getKey())
            .source(x.getValue().get(0).getFrom().getText())
            .build())
        .collect(Collectors.groupingBy(x -> findGroupKey(x.getTarget())))
        .entrySet()
        .stream()
        .map(method -> MappingMethods.builder()
            .outputType(method.getKey())
            .mappings(method.getValue().stream()
                .map(y -> TargetSourcecontainer.builder()
                    .source(getSource(y))
                    .target(getTarget(y))
                    .build())
                .collect(Collectors.toList()))
            .build())
        .collect(Collectors.toList());

    return Result.builder()
        .mapperName("TODO")
        .mappings(methodList)
        .outputType("Output1")
        .build();
  }

  private static String getTarget(ResultMapping y) {
    if (y.getTarget().contains(".")) {
      return y.getTarget().split("\\.")[1];
    }
    return y.getTarget();
  }

  private static String getSource(ResultMapping y) {
    return "TODO";
  }

  @NotNull
  private static String findGroupKey(String key) {
    if (key.contains(".")) {
      return key.substring(0, key.lastIndexOf("."));
    }
    return key;
  }

  public static Map<String, List<Mapping>> extracted(Map<String, List<Mapping>> input) {

    var newList = new HashMap<String, List<Mapping>>();
    var nothingDone = true;

    for (var item : input.entrySet()) {
      for (var mapping : item.getValue()) {
        if (mapping.isDone()) {
          newList.putIfAbsent(item.getKey(), new ArrayList<>());
          newList.get(item.getKey()).add(mapping);
          continue;
        }

        nothingDone = false;

        var extract = Extractor.expandElement(item.getKey(), mapping);

        newList = mergeLists(newList, extract);
      }
    }

    if (nothingDone) {
      return newList;
    }

    return extracted(newList);
  }

  private static HashMap<String, List<Mapping>> mergeLists(
      HashMap<String, List<Mapping>> left,
      HashMap<String, List<Mapping>> right
  ) {
    for (var entry : right.entrySet()) {
      left.putIfAbsent(entry.getKey(), new ArrayList<>());
      for (var mapping : entry.getValue()) {
        left.get(entry.getKey()).add(mapping);
      }
    }
    return left;
  }
}
