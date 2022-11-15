package fr.univamu.graph.generators;

import fr.univamu.graph.Edge;
import fr.univamu.graph.EmbeddedGraph;
import fr.univamu.graph.UndirectedGraph;
import fr.univamu.helpers.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Lollipop implements EmbeddedGraph {

	private final int order;
	private final UndirectedGraph graph;
	private final int pathLength;
	private final List<Integer> permutation;

	public Point layout(int vertex) {
		int index = permutation.get(vertex);
		return (index < pathLength) ?
					 Point.polar(2 * index  * Math.PI / pathLength,2):
					 Point.polar(2 * (index - pathLength) * Math.PI / (order-pathLength),1);
	}

	public int order() {
		return order;
	}

	public int pathLength() {
		return pathLength;
	}

	public UndirectedGraph graph() {
		return this.graph;
	}

	public Lollipop(int order) {
		if (order < 3) {
			throw new IllegalArgumentException("Lollipop graphs must have at least 3 vertices.");
		}
		this.order = order;
		this.graph = new UndirectedGraph(order);
		this.permutation = new ArrayList<>(order);
		List<Integer> shuffled = new ArrayList<>(order);
		for (int i = 0; i < order; i++) {
			permutation.add(i);
			shuffled.add(i);
		}
		Collections.shuffle(shuffled);
		for (int i = 0; i < order; i++) {
			permutation.set(shuffled.get(i), i);
		}
		pathLength = order / 3;
		for (int i = 0; i < pathLength; i++) {
			graph.addEdge(shuffled.get(i), shuffled.get(i + 1));
		}
		List<Edge> edges = new ArrayList<>();
		for (int i = pathLength; i < order; i++) {
			for (int j = i + 1; j < order; j++) {
				edges.add(new Edge(shuffled.get(i), shuffled.get(j)));
			}
		}
		Collections.shuffle(edges);
		edges.forEach(graph::add);
	}
	
	
}
