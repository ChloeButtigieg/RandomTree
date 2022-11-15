package fr.univamu.graph;

import fr.univamu.helpers.Point;
import fr.univamu.visualizer.Embedding;

public interface EmbeddedGraph {

  UndirectedGraph graph();
  Point layout(int vertex);

  default Embedding layout() {
    return this::layout;
  }

}
