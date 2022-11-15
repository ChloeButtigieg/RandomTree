package fr.univamu.graph;

public record Edge(int vertex1, int vertex2) {

  public int opposite(int vertex) {
    if (vertex == vertex1) return vertex2;
    if (vertex == vertex2) return vertex1;
    throw new IllegalArgumentException(this + ".opposite(" + vertex +")");
  }
}
