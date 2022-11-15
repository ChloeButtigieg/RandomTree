package fr.univamu.graph.rootedtrees;

import fr.univamu.graph.Edge;
import fr.univamu.graph.UndirectedGraph;
import fr.univamu.graph.search.Search;
import fr.univamu.helpers.Iterations;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;
import java.util.stream.Collectors;


public record RootedTree(int root, List<RootedTree> children)
  implements Iterable<RootedTree> {

	public int size() {
		int count = 0;
		for (RootedTree node : this) {
			count = count + 1;
		}
		return count;
	}

	public Iterator<RootedTree> iterator() {
		return iterator(Deque::offerLast);
	}

	public Iterable<RootedTree> bfsIterable() {
		return this::iterator;
	}

	public Iterable<RootedTree> dfsIterable() {
		return () -> iterator(Deque::offerFirst);
	}

	public int maxNode() {
		int max = root;
		for (RootedTree node : this) {
			max = Math.max(max, node.root());
		}
		return max;
	}

	public static RootedTree of(Iterable<Edge> tree) {
		Optional<Edge> anyEdge = Iterations.toStream(tree).findAny();
		if (anyEdge.isEmpty()) {
			throw new IllegalArgumentException("RootedTree.of(edges) called on empty set of edges.");
		}
		int root = anyEdge.get().vertex1();
		RootedTree uncenteredTree = of(root, tree);
		return RootedTreeAnalyser.moveRootToCenter(uncenteredTree);
	}


	public static RootedTree of(int root, Iterable<Edge> tree) {
		int maxVertex =
				Iterations.toStream(tree)
						.map(e -> Math.max(e.vertex1(), e.vertex2()))
						.max(Integer::compareTo)
						.orElse(root);
		UndirectedGraph graph = new UndirectedGraph(maxVertex+1);
		tree.forEach(graph::add);
		Search bfs = Search.breadthFirstSearch(graph);
		bfs.searchFrom(root);
		return getNode(root, graph, bfs::predecessor);
	}

	private static RootedTree getNode(
			int root,
			UndirectedGraph graph,
			IntFunction<Optional<Integer>> toParent
	) {
		Optional<Integer> rootParent = toParent.apply(root);
		List<RootedTree> children =
				Iterations.toStream(graph.neighboursOf(root))
				.filter(v -> !rootParent.equals(Optional.of(v)))
				.map(v -> getNode(v,graph,toParent))
				.toList();
		;
		return new RootedTree(root, children);
	}


	public RootedTree swap(RootedTree child) {
		if (!children.contains(child)) {
			throw new IllegalArgumentException("swap(" + child + "): not a children of " + this);
		}
		List<RootedTree> thisChildren = new ArrayList<>(this.children);
		thisChildren.remove(child);
		List<RootedTree> childChildren = new ArrayList<>(child.children);
		childChildren.add(new RootedTree(this.root, thisChildren));
		return new RootedTree(child.root, childChildren);
	}

	@Override
	public String toString() {
		return children.stream()
				.map(RootedTree::toString)
				.collect(Collectors.joining(",",root + "(",")"));
	}

	public Iterator<RootedTree> iterator(
			BiConsumer<Deque<RootedTree>,RootedTree> offer
	) {
		return new Iterator<RootedTree>() {

			private final Deque<RootedTree> queue = new ArrayDeque<>();
			{ offer.accept(queue, RootedTree.this); }

			@Override
			public boolean hasNext() {
				return !queue.isEmpty();
			}

			@Override
			public RootedTree next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				RootedTree tree = queue.pollFirst();
				tree.children().forEach(child -> offer.accept(queue,child));
				return tree;
			}
		};
	}

}
