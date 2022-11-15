package fr.univamu.visualizer;

import fr.univamu.graph.Edge;
import fr.univamu.graph.rootedtrees.NodeStats;
import fr.univamu.graph.rootedtrees.RootedTree;
import javafx.scene.paint.Color;

public class RainbowDrawingParameters implements DrawingParameters {

  public record Params(
      double vertexRadius,
      double edgeLineWidth,
      int colorCycleLength,
      double brightnessSlope,
      double minBrightness
  ) {

    public static Params byDefault() {
      return new Params(4,6,150,3,40);
    }

  }

  private final Params params;
  private final NodeStats stats;

  public RainbowDrawingParameters(Iterable<Edge> edges, Params params) {
    this.stats = new NodeStats(RootedTree.of(edges));
    this.params = params;
  }

  public RainbowDrawingParameters(Iterable<Edge> edges) {
    this(edges, Params.byDefault());
  }

  @Override
  public double verticesRadius() {
    return params.vertexRadius();
  }

  @Override
  public double edgesLineWidth() {
    return params.edgeLineWidth();
  }

  @Override
  public Color color(int vertex) {
		int depth = stats.depth(vertex);
		int height = stats.height(vertex) + 1;
		double hue =
        360. * ((double) (depth % params.colorCycleLength)) / params.colorCycleLength;
		double saturation =  0.7;
		double brightness =
				Math.min(100.0, params.brightnessSlope * height + params.minBrightness) / 100.0;
		Color col = Color.hsb(hue, saturation, brightness);
		return col;
  }
}
