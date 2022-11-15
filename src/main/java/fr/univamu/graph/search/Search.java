package fr.univamu.graph.search;

import fr.univamu.graph.Edge;
import fr.univamu.graph.UndirectedGraph;

import java.util.*;

public class Search {


	public void searchFrom(int source) {
		process(pathTo(source));
		frontier.processAll(this::process);
	}

	public static Search depthFirstSearch(UndirectedGraph graph) {
		return new Search(graph, new FifoFrontier());
	}

	public static Search breadthFirstSearch(UndirectedGraph graph) {
		return new Search(graph, new LifoFrontier());
	}

	public static boolean isConnected(UndirectedGraph graph) {
		Search search = breadthFirstSearch(graph);
		search.searchFrom(0);
		return search.edges().size() == graph.order() - 1;
	}

	public boolean isReached(int vertex) {
		return reached.get(vertex);
	}

	public Path pathTo(int vertex) {
		return paths.get(vertex);
	}

	public Optional<Integer> predecessor(int vertex) {
		return switch (paths.get(vertex)) {
			case Path.EmptyPath emptyPath ->
					Optional.empty();
			case Path.NonEmptyPath nonEmptyPath ->
					Optional.of(nonEmptyPath.tail().extremity());
		};
	}

	public Set<Edge> edges() {
		Set<Edge> edges = new HashSet<>();
		for (int vertex : graph.vertices()) {
			if (paths.get(vertex) instanceof Path.NonEmptyPath nePath) {
				edges.add(nePath.startEdge());
			}
		}
		return edges;
	}


	public Search(UndirectedGraph graph, Frontier frontier) {
		this.graph = graph;
		this.frontier = frontier;
		this.reached = new BitSet(graph.order());
		this.paths = new ArrayList<>(graph.order());
		for (int vertex = 0; vertex < graph.order(); vertex++) {
			this.paths.add(Path.start(vertex));
		}
	}

	private final UndirectedGraph graph;
	Frontier frontier;
	BitSet reached;
	List<Path> paths;


	private void process(Path path) {
		int currentVertex = path.extremity();
		if (reached.get(currentVertex)) { return;	}
		paths.set(currentVertex, path);
		reached.set(currentVertex);
		for (Edge edge : graph.incidentEdgesOf(currentVertex)) {
			frontier.offer(path.add(edge));
		}
	}


}
