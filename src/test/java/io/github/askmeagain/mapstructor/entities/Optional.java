package io.github.askmeagain.mapstructor.entities;

import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

@RequiredArgsConstructor
public final class Optional<T> {

  private final T value;

  public static <T> Optional<T> of(T value) {
    return new Optional<>(value);
  }

  public <U> Optional<U> map(Function<? super T, ? extends U> mapper) {
    return Optional.of(mapper.apply(value));
  }

  public T orElse(T other) {
    return value != null ? value : other;
  }
}
