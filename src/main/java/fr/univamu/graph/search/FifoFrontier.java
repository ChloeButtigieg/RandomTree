package fr.univamu.graph.search;

import java.util.ArrayDeque;
import java.util.Deque;

public class FifoFrontier implements Frontier {

  private final Deque<Path> queue = new ArrayDeque<>();

  @Override
  public boolean isEmpty() {
    return queue.isEmpty();
  }

  @Override
  public void offer(Path path) {
    queue.offerLast(path);
  }

  @Override
  public Path poll() {
    return queue.pollLast();
  }
}
