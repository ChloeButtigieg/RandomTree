package fr.univamu.graph.generators;

import fr.univamu.graph.Edge;
import fr.univamu.graph.EmbeddedGraph;
import fr.univamu.graph.UndirectedGraph;
import fr.univamu.helpers.Point;

public class Grid implements EmbeddedGraph {

	private final int width;
	private final int height;
	private final UndirectedGraph graph;

	public UndirectedGraph graph() {
		return graph;
	}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

	public Point layout(int vertex) {
		return new Point(x(vertex), y(vertex));
	}

	public int toVertex(int x, int y) {
		return x + y * width;
	}

	public int x(int vertex) {
		return vertex % width;
	}

	public int y(int vertex) {
		return vertex / width;
	}
	

	public Grid(int width, int height) {
		if (width < 1 || height < 1) {
			throw new IllegalArgumentException("Grid graphs must have positive dimensions.");
		}
		this.width = width;
		this.height = height;
		this.graph = new UndirectedGraph(width * height);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height - 1; j++) {
				graph.addEdge(toVertex(i, j), toVertex(i, j + 1));
			}
		}
		for (int i = 0; i < width - 1; i++) {
			for (int j = 0; j < height; j++) {
				graph.addEdge(toVertex(i, j), toVertex(i + 1, j));
			}
		}
	}

	
	public boolean isHorizontal(Edge e) {
		return y(e.vertex1()) == y(e.vertex2());
	}
	
	public boolean isVertical(Edge e) {
		return x(e.vertex1()) == x(e.vertex2());
	}

}
