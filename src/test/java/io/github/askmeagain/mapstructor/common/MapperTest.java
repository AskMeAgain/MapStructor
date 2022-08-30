package io.github.askmeagain.mapstructor.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MapperTest {
  String value();
}
