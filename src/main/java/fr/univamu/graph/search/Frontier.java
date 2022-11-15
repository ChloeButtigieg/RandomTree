package fr.univamu.graph.search;

import java.util.function.Consumer;

public interface Frontier {

  boolean isEmpty();

  void offer(Path path);
  Path poll();

  default void processAll(Consumer<Path> action) {
    while (!isEmpty()) {
      action.accept(poll());
    }
  }

}
