package fr.univamu.visualizer;

import fr.univamu.graph.Edge;
import fr.univamu.graph.EmbeddedGraph;
import fr.univamu.graph.generators.Complete;
import fr.univamu.graph.generators.ErdosRenyi;
import fr.univamu.graph.generators.Grid;
import fr.univamu.graph.generators.Lollipop;
import fr.univamu.graph.search.Search;
import fr.univamu.helpers.Point;
import fr.univamu.randomtree.Prim;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class Controller implements Initializable {

  public static final int HEIGHT = 800;
  public static final int WIDTH = 1000;
  @FXML
  public Canvas canvas;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    drawErdosReniy();
    // drawGrid();
    // drawLollipop();
    //drawComplete();
  }

  private void drawErdosReniy() {
    EmbeddedGraph graph = new ErdosRenyi(30,0.3);
    Search search = Search.breadthFirstSearch(graph.graph());
    search.searchFrom(0);
    //Set<Edge> edges = search.edges();
    final Prim prim = new Prim(graph.graph());
    Set<Edge> edges = prim.getRandomTree();
    Embedding layout =
        graph.layout().scale(300).translate(Point.cartesian(500,400));
    drawGraphAndSubGraph(graph, edges, layout);
  }
  private void drawComplete() {
    EmbeddedGraph graph = new Complete(10);
    Search search = Search.depthFirstSearch(graph.graph());
    search.searchFrom(0);
    Set<Edge> edges = search.edges();
    Embedding layout =
        graph.layout().scale(300).translate(Point.cartesian(500,400));
    drawGraphAndSubGraph(graph, edges, layout);
  }

  private void drawGraphAndSubGraph(
      EmbeddedGraph graph,
      Set<Edge> edges,
      Embedding layout
  ) {
    EdgesDrawer graphDrawer = new EdgesDrawer(
        graph.graph().edges(),
        new GrayDrawingParameters(),
        layout
    );
    EdgesDrawer treeDrawer = new EdgesDrawer(
        edges,
        new BlackDrawingParameters(),
        layout
    );
    graphDrawer.draw(canvas.getGraphicsContext2D());
    treeDrawer.draw(canvas.getGraphicsContext2D());
  }

  private void drawLollipop() {
    EmbeddedGraph lollipop = new Lollipop(100);
    Search search = Search.depthFirstSearch(lollipop.graph());
    search.searchFrom(83);
    Set<Edge> edges = search.edges();
    Embedding layout =
        lollipop.layout()
            .scale(180)
            .translate(Point.cartesian(500,400));
    drawGraphAndSubGraph(lollipop,edges,layout);
  }

  private void drawGrid() {
    EmbeddedGraph grid = new Grid(100,80);
    Search search = Search.breadthFirstSearch(grid.graph());
    search.searchFrom(0);
    Set<Edge> edges = search.edges();
    Embedding layout =
        grid.layout().scale(10).translate(Point.cartesian(4,4));
    EdgesDrawer treeDrawer = new EdgesDrawer(
        edges,
        new RainbowDrawingParameters(edges),
        layout
    );
    treeDrawer.draw(canvas.getGraphicsContext2D());
  }


}