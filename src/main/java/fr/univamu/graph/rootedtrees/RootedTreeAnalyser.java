package fr.univamu.graph.rootedtrees;

import fr.univamu.helpers.Iterations;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.IntUnaryOperator;

public class RootedTreeAnalyser {

  public static RootedTree moveRootToCenter(RootedTree tree) {
    NodeStats nodeStats = new NodeStats(tree);
    RootedTreeAnalyser builder = new RootedTreeAnalyser(tree,nodeStats);
    return builder.centralNode();
  }

  public static RootedTree moveRootToCentroid(RootedTree tree) {
    NodeStats nodeStats = new NodeStats(tree);
    RootedTreeAnalyser builder = new RootedTreeAnalyser(tree,nodeStats);
    return builder.centroidalNode();
  }

  public static TreeStats statsOf(RootedTree tree) {
    RootedTree centeredTree = moveRootToCenter(tree);
    NodeStats centeredNodeStats = new NodeStats(centeredTree);
    RootedTreeAnalyser centeredBuilder =
        new RootedTreeAnalyser(centeredTree,centeredNodeStats);
    return centeredBuilder.makeTreeStats();
  }


  private final RootedTree tree;
  private final NodeStats stats;

  private RootedTreeAnalyser(RootedTree tree, NodeStats stats) {
    this.tree = tree;
    this.stats = stats;
  }

  public TreeStats makeTreeStats() {
    return new TreeStats(
        1,
        getWienerIndex(),
        getDiameter(),
        getRadius(),
        getDistanceFromCenterToCentroid(),
        getAverageEccentricity(),
        getDistribution(stats::degree),
        getDistribution(stats::height),
        getDistribution(stats::depth)
    );
  }

  private RootedTree centralNode() {
    RootedTree node = tree;
    RootedTree swapped = tree;
    int heightUp = 0;
    while (true) {
      Optional<RootedTree> firstChildOption =
          node.children().stream()
              .max(Comparator.comparingInt(child -> stats.height(child.root())));
      if (firstChildOption.isEmpty()) { break; }
      RootedTree firstChild = firstChildOption.get();
      int secondChildHeight =
          node.children().stream()
              .filter(child -> child != firstChild)
              .mapToInt(child -> stats.height(child.root()))
              .max().orElse(-1);
      heightUp = Math.max(2 + secondChildHeight, 1 + heightUp);
      if (stats.height(firstChild.root()) + 1 <= heightUp) {
        break;
      }
      swapped = swapped.swap(firstChild);
      node = firstChild;
    }
    return swapped;
  }

  private RootedTree centroidalNode() {
    RootedTree node = tree;
    RootedTree swapped = tree;
    int halfOrder = (stats.size(tree.root()) + 1) / 2;
    while (true) {
      Optional<RootedTree> bestChild =
          node.children().stream()
              .max(Comparator.comparingInt(child -> stats.size(child.root())));
      if (bestChild.isEmpty() || stats.size(bestChild.get().root()) < halfOrder) {
        break;
      }
      swapped = swapped.swap(bestChild.get());
      node = bestChild.get();
    }
    return swapped;
  }


  private List<Integer> twoChildrenHeight() {
    return tree.children().stream()
        .mapToInt(child -> -stats.height(child.root()))
        .sorted()
        .limit(2)
        .map(n -> -n)
        .boxed().toList();
  }

  public boolean isCentral() {
    List<Integer> highest = twoChildrenHeight();
    return highest.size() < 2 || highest.get(0) <= highest.get(1) + 1;
  }


  public int getRadius() {
    checkCentrality("getRadius");
    return stats.height(tree.root());
  }

  private void checkCentrality(String methodName) {
    if (!isCentral()) {
      throw new IllegalArgumentException(methodName + "() called on non-central tree");
    }
  }


  public int getDiameter() {
    checkCentrality("getDiameter");
    return twoChildrenHeight().stream().mapToInt(h -> h + 1).sum();
  }


  public int getDistanceFromCenterToCentroid() {
    checkCentrality("getDistanceFromCenterToCentroid");
    return stats.depth(centroidalNode().root());
  }

  public double getAverageEccentricity() {
    checkCentrality("getAverageEccentricity");
    int sumEccentricity = 0;
    for (RootedTree node : tree) {
      sumEccentricity = sumEccentricity + stats.depth(node.root());
    }
    return (double) sumEccentricity / (double) stats.size(tree.root());
  }

  // sum of distances between all pairs of vertices.
  private long getWienerIndex() {
    long count = 0;
    long order = tree.size();
    for (RootedTree node : tree) {
      if (node == tree) continue;
      int size = stats.size(node.root());
      count = count + size * (order - size);
    }
    return count;
  }

  private double[] getDistribution(IntUnaryOperator param) {
    int maxValue = Iterations.toStream(tree)
        .map(RootedTree::root)
        .mapToInt(param::applyAsInt)
        .max().orElse(0);
    double[] values = new double[1+maxValue];
    tree.iterator().forEachRemaining(node -> {
          int value = param.applyAsInt(node.root());
          values[value] = 1 + values[value];
        }
    );
    return values;
  }

}
