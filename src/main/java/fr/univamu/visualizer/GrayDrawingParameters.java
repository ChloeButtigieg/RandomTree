package fr.univamu.visualizer;

import javafx.scene.paint.Color;

public class GrayDrawingParameters implements DrawingParameters {
  @Override
  public double verticesRadius() {
    return 4;
  }

  @Override
  public double edgesLineWidth() {
    return 6;
  }

  @Override
  public Color color(int vertex) {
    return Color.LIGHTGRAY;
  }
}
