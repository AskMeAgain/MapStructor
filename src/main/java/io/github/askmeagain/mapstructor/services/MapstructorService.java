package io.github.askmeagain.mapstructor.services;

import com.intellij.psi.PsiCodeBlock;
import io.github.askmeagain.mapstructor.MapstructorAction;
import io.github.askmeagain.mapstructor.entities.Mapping;
import io.github.askmeagain.mapstructor.entities.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapstructorService {

  public Result calc(PsiCodeBlock codeBlock) {
    var result = Extractor.expandElement("", Mapping.of(codeBlock));

    var finalMapping = extracted(result);

    return Result.builder()
        .mapperName("TODO")
        .mappings(List.of("TODO"))
        .outputType("TODO")
        .build();
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
