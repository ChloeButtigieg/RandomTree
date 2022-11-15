package fr.univamu.visualizer;

import fr.univamu.graph.Edge;
import fr.univamu.helpers.Iterations;
import fr.univamu.helpers.Point;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;



public class EdgesDrawer {

  private final DrawingParameters params;
  private final Iterable<Edge> tree;
  private final Embedding embedding;


  public EdgesDrawer(
      Iterable<Edge> tree,
      DrawingParameters params,
      Embedding embedding
  ) {
    this.params = params;
    this.tree = tree;
    this.embedding = embedding;
  }

  public void draw(GraphicsContext ctx) {
    int maxVertex =
        Iterations.toStream(tree)
            .mapToInt(e -> Math.max(e.vertex1(), e.vertex2()))
            .max().orElse(-1);
    for (int vertex = 0; vertex <= maxVertex; vertex++) {
      drawVertex(ctx, vertex);
    }
    tree.forEach(edge -> drawEdge(ctx,edge));
  }

  private void drawEdge(GraphicsContext ctx, Edge edge) {
    Point pos1 = embedding.position(edge.vertex1());
    Point pos2 = embedding.position(edge.vertex2());
    Color col1 = params.color(edge.vertex1());
    Color col2 = params.color(edge.vertex2());
    LinearGradient gradient =
        new LinearGradient(pos1.x(),pos1.y(),pos2.x(),pos2.y(),
            false, CycleMethod.NO_CYCLE,
            new Stop(0,col1),
            new Stop(1,col2)
            );
    ctx.setStroke(gradient);
    ctx.setLineWidth(params.edgesLineWidth());
    ctx.beginPath();
    ctx.moveTo(pos1.x(), pos1.y());
    ctx.lineTo(pos2.x(),pos2.y());
    ctx.closePath();
    ctx.stroke();
  }

  private void drawVertex(GraphicsContext ctx, int vertex) {
    Point pos = embedding.position(vertex);
    Color col = params.color(vertex);
    ctx.setFill(col);
    ctx.fillOval(
        pos.x() - params.verticesRadius(),
        pos.y() - params.verticesRadius(),
        2 * params.verticesRadius(),
        2 * params.verticesRadius());
  }

}
