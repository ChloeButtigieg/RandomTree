package fr.univamu.graph.rootedtrees;

import java.util.*;


public class NodeStats {

  public int height(int vertex) {
    return stats(vertex).height();
  }

  public int depth(int vertex) {
    return stats(vertex).depth();
  }

  public int degree(int vertex) {
    return stats(vertex).degree();
  }

  public int size(int vertex) {
    return stats(vertex).size();
  }



  public NodeStats(RootedTree tree) {
    this.tree = tree;
    int maxVertex = tree.maxNode();
    for (int vertex = 0; vertex < maxVertex + 1; vertex++) {
      stats.add(null);
    }
    computeStats(tree, 0);
  }



  private final RootedTree tree;
  private final List<Stats> stats = new ArrayList<>();

  private record Stats(int height, int depth, int degree, int size) {

    private static Stats of(int depth) {
      return new Stats(0,depth,0,0);
    }

    private Stats add(Stats stats) {
      return new Stats(
          Math.max(this.height, 1 + stats.height),
          this.depth,
          this.degree + 1,
          this.size + stats.size
      );
    }

    private Stats incrSize() {
      return new Stats(height, depth, degree, size + 1);
    }
  }


  private Stats computeStats(RootedTree node, int depth) {
    Stats stats =
        node.children().stream()
        .map(child -> computeStats(child, depth+1))
        .reduce(Stats.of(depth), Stats::add)
        .incrSize();
    this.stats.set(node.root(), stats);
    return stats;
  }

  private Stats stats(int vertex) {
    return stats.get(vertex);
  }


}
