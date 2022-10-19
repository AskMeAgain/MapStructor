package io.github.askmeagain.mapstructor.services;

import io.github.askmeagain.mapstructor.entities.MapStructMapperEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.nio.charset.Charset;

@RequiredArgsConstructor
public class MapstructorTestGeneratorService {

  private final MapStructMapperEntity mapStructMapperEntity;

  @SneakyThrows
  public String generateTest() {
    var is = getClass()
        .getClassLoader()
        .getResourceAsStream("test-template.txt");
    return IOUtils.toString(is, Charset.defaultCharset());
  }
}
