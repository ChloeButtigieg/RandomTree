package fr.univamu.randomtree;

import fr.univamu.graph.Edge;
import fr.univamu.graph.UndirectedGraph;

import java.util.*;

public class Prim {
    private final UndirectedGraph graph;
    private final int  source;
    private final int infinity;
    private final int[] distance;
    private final int[] previous;
    private boolean[] isVertexVisited;
    private final Map<Edge, Integer> weight;

    public Prim(final UndirectedGraph graph) {
        this.graph = graph;
        this.source = getRandomVertex();
        this.infinity = graph.getEdgeCardinality() + 1;
        this.distance = new int[graph.order()];
        this.previous = new int[graph.order()];
        this.isVertexVisited = new boolean[graph.order()];
        this.weight = new HashMap<>();
        initPrim();
        initWeight();
    }

    private int getRandomVertex() {
        final Random random = new Random();
        return random.nextInt(graph.order());
    }

    private void initPrim() {
        for (int index = 0; index < graph.order(); index++) {
            distance[index] = infinity;
            previous[index] = -1;
            isVertexVisited[index] = false;
        }
        distance[source] = 0;
    }

    private void initWeight() {
        final Random random = new Random();
        for (Edge edge : graph.edges()) {
            final int nextInt = random.nextInt(2);
            if (nextInt == 0) weight.put(edge, 0);
            else weight.put(edge, 1);
        }
    }

    public Set<Edge> getRandomTree() {
        computeRandomTree();
        final Set<Edge> randomTree = new HashSet<>();
        for (Integer vertex : graph.vertices()) {
            int currentNode = vertex;
            while (currentNode != source) {
                randomTree.add(graph.getEdge(previous[currentNode], currentNode));
                currentNode = previous[currentNode];
            }
        }
        return randomTree;
    }

    private void computeRandomTree() {
        for (int index = 0; index < graph.order(); index++) {
            int currentVertex = extractMin(isVertexVisited, distance);
            isVertexVisited[currentVertex] = true;
            for (Edge edge : graph.incidentEdgesOf(currentVertex)) {
                final int vertex;
                if (edge.vertex1() == currentVertex) vertex = edge.vertex2();
                else vertex = edge.vertex1();
                if (distance[vertex] > this.weight.get(edge)) {
                    distance[vertex] = this.weight.get(edge);
                    previous[vertex] = currentVertex;
                }
            }
        }
    }

    private int extractMin(final boolean[] isVertexVisited, final int[] distance) {
        int minNode = -1;
        for (int index = 0; index < graph.order(); index++) {
            if (!isVertexVisited[index]) {
                minNode = index;
                break;
            }
        }
        for (int node = 1; node < graph.order(); node++) {
            if (distance[minNode] > distance[node] && !isVertexVisited[node]) {
                minNode = node;
            }
        }
        return minNode;
    }
}
