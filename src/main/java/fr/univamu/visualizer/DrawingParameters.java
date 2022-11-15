package fr.univamu.visualizer;

import javafx.scene.paint.Color;

public interface DrawingParameters {
  double verticesRadius();
  double edgesLineWidth();
  Color color(int vertex);
}
