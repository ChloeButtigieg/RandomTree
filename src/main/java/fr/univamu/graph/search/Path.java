package fr.univamu.graph.search;

import fr.univamu.graph.Edge;

import java.util.Iterator;
import java.util.NoSuchElementException;

public sealed interface Path
  extends Iterable<Edge>
  permits Path.EmptyPath, Path.NonEmptyPath
{

  record EmptyPath(int endVertex) implements Path {}

  record NonEmptyPath(int startVertex, Edge startEdge, Path tail) implements Path {}

  static EmptyPath start(int vertex) {
    return new EmptyPath(vertex);
  }

  default NonEmptyPath add(Edge edge) {
    int vertex = this.extremity();
    if (edge.vertex1() != vertex && edge.vertex2() != vertex) {
      throw new IllegalArgumentException("Cannot add " + edge + "to path " + this);
    }
    return new NonEmptyPath(edge.opposite(vertex), edge, this);
  }

  default int extremity() {
    return switch (this) {
      case EmptyPath emptyPath -> emptyPath.endVertex;
      case NonEmptyPath nonEmptyPath -> nonEmptyPath.startVertex;
    };
  }

  default Iterator<Integer> vertexIterator() {
    return new Iterator<Integer>() {

      private Path path = Path.this;
      private boolean isAlive = true;

      @Override
      public boolean hasNext() {
        return isAlive;
      }

      @Override
      public Integer next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        return switch (path) {
          case NonEmptyPath nePath -> {
            path = nePath.tail();
            yield nePath.startVertex;
          }
          case EmptyPath ePath -> {
            isAlive = false;
            yield ePath.endVertex;
          }
        };
      }
    };
  }

  default Iterator<Edge> edgeIterator() {
    return this.iterator();
  }

  default Iterator<Edge> iterator() {
    return new Iterator<Edge>() {
      private Path path = Path.this;

      @Override
      public boolean hasNext() {
        return path instanceof NonEmptyPath;
      }

      @Override
      public Edge next() {
        if (!(path instanceof NonEmptyPath nePath)) {
          throw new NoSuchElementException();
        }
        path = nePath.tail();
        return nePath.startEdge;
      }
    };
  }


}


