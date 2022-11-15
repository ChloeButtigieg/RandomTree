package fr.univamu.visualizer;

import javafx.scene.paint.Color;

public class BlackDrawingParameters implements DrawingParameters {
  @Override
  public double verticesRadius() {
    return 4;
  }

  @Override
  public double edgesLineWidth() {
    return 3;
  }

  @Override
  public Color color(int vertex) {
    return Color.BLACK;
  }
}
