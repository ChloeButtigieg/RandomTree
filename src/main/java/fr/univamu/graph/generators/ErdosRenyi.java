package fr.univamu.graph.generators;

import fr.univamu.graph.EmbeddedGraph;
import fr.univamu.graph.UndirectedGraph;
import fr.univamu.helpers.Point;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;


public class ErdosRenyi implements EmbeddedGraph {

	private final UndirectedGraph graph;
	private final int order;
	private final double edgeProbability;


	public UndirectedGraph graph() {
		return graph;
	}

	public int order() {
		return order;
	}

	public double edgeProbability() {
		return edgeProbability;
	}

	public Point layout(int vertex) {
		return Point.polar(2 * vertex * Math.PI / order, 1);
	}

	private static final Random gen = new Random();

	public ErdosRenyi(int order, double edgeProbability) {
		this.graph = new UndirectedGraph(order);
		this.order = order;
		this.edgeProbability = edgeProbability;
		for (int i = 0; i < order; i++) {
			for (int j = i + 1; j < order; j++) {
				if (gen.nextDouble() < edgeProbability) {
					graph.addEdge(i, j);
				}
			}
		}
	}
	
}
