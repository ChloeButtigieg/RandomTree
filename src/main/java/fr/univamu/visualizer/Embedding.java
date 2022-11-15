package fr.univamu.visualizer;

import fr.univamu.helpers.Point;

@FunctionalInterface
public interface Embedding {

  Point position(int vertex);

  default Embedding scale(double factor) {
    return vertex -> position(vertex).scale(factor);
  }

  default Embedding translate(Point vector) {
    return vertex -> position(vertex).add(vector);
  }

  default Embedding rotate(double radian) {
    return vertex -> position(vertex).rotate(radian);
  }

  default Embedding rotate(double radian, Point center) {
    return vertex -> position(vertex).rotate(radian, center);
  }

}
