package fr.univamu.graph;

import fr.univamu.helpers.Iterations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class UndirectedGraph {

	private int order;
	private int edgeCardinality;
	
	List<List<Edge>> adjacencies;

	
	public boolean isVertex(int index) {
		return index >= 0 && index < order;
	}
	

	public UndirectedGraph(int order) {
		this.order = order;
		this.adjacencies = new ArrayList<>(order);
		for (int i = 0; i < order; i++) {
			adjacencies.add(new ArrayList<>());
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Vertices: 0 to ").append(this.order-1);
		for (int vertex : this.vertices()) {
			builder.append("\n")
					.append(vertex)
					.append(": ")
					.append(
							Iterations.toStream(this.neighboursOf(vertex))
									.map(Object::toString)
									.collect(Collectors.joining(","))
					);
		}
		return builder.toString();
	}

	public int addVertex(int indexVertex) {
		this.adjacencies.add(new ArrayList<>());
		order = order + 1;
		return order - 1;
	}


	public void addEdge(int vertex1, int vertex2) {
		Edge edge = new Edge(vertex1, vertex2);
		add(edge);
	}

	public void add(Edge edge) {
		adjacencies.get(edge.vertex1()).add(edge);
		adjacencies.get(edge.vertex2()).add(edge);
		edgeCardinality = edgeCardinality + 1;
	}

	public Iterable<Edge> incidentEdgesOf(int vertex) {
		return () -> adjacencies.get(vertex).iterator();
	}

	public Iterable<Integer> neighboursOf(int vertex) {
		Function<Edge,Integer> toOpposite = edge -> edge.opposite(vertex);
		return () -> Iterations.map(incidentEdgesOf(vertex).iterator(),toOpposite);
	}

	public Iterable<Integer> vertices() {
		return () -> Iterations.range(0,order);
	}

	public Iterable<Edge> edges() {
		return Iterations.concat(adjacencies);
	}

	public int degree(int vertex) {
		return adjacencies.get(vertex).size();
	}
	

	public boolean areNeighbours(int vertex1, int vertex2) {
		for (int vertex : neighboursOf(vertex1)) {
			if (vertex == vertex2) return true;
		}
		return false;
	}


	public int order() {
		return order;
	}

	public int getEdgeCardinality() { return edgeCardinality;}

	public Edge getEdge(final int vertex1, final int vertex2) {
		for (Edge edge : edges()) {
			if ((edge.vertex1() == vertex1 && edge.vertex2() == vertex2) || edge.vertex1() == vertex2 && edge.vertex2() == vertex1)
				return edge;
		}
		return null;
	}

}
