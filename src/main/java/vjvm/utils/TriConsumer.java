package vjvm.utils;

import java.util.Objects;

@FunctionalInterface
public interface TriConsumer<S, T, U> {
  void accept(S s, T t, U u);

  default TriConsumer<S, T, U> andThen(TriConsumer<? super S, ? super T, ? super U> after) {
    Objects.requireNonNull(after);

    return (a, b, c) -> {
      accept(a, b, c);
      after.accept(a, b, c);
    };
  }
}
