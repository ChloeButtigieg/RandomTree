package fr.univamu.graph.generators;

import fr.univamu.graph.Edge;
import fr.univamu.graph.EmbeddedGraph;
import fr.univamu.graph.UndirectedGraph;
import fr.univamu.helpers.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Complete implements EmbeddedGraph {

	private final UndirectedGraph graph;
	private final int order;

	public int order() {
		return order;
	}

	public UndirectedGraph graph() {
		return graph;
	}

	public Point layout(int vertex) {
		return Point.polar(2 * vertex * Math.PI / order, 1);
	}

	public Complete(int order) {
		if (order < 1) {
			throw new IllegalArgumentException("Complete graphs must have at least one vertex.");
		}
		this.order = order;
		this.graph = new UndirectedGraph(order);
		List<Edge> edges = new ArrayList<>();
		for(int i = 0; i < order; i++) {
			for (int j = i + 1; j < order; j++) {
				edges.add(new Edge(i, j));
			}
		}
		Collections.shuffle(edges);
		edges.forEach(graph::add);
	}

}
