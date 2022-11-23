package fr.univamu.graph;

import fr.univamu.graph.generators.Complete;
import fr.univamu.graph.generators.ErdosRenyi;
import fr.univamu.graph.rootedtrees.RootedTree;
import fr.univamu.graph.rootedtrees.RootedTreeAnalyser;
import fr.univamu.graph.rootedtrees.TreeStats;
import fr.univamu.graph.search.Search;
import fr.univamu.randomtree.Prim;

import java.util.Set;


public class Main {

	public static void main(String[] argv) {
		/*
		TreeStats stats = TreeStats.empty();
		for (int i = 0; i < 100; i++) {
			UndirectedGraph graph = new ErdosRenyi(20,0.2).graph();
			Search search = Search.breadthFirstSearch(graph);
			search.searchFrom(0);
			if (search.edges().size() != 19) {
				continue;
			}
			RootedTree tree = RootedTree.of(search.edges());
			stats = stats.add(RootedTreeAnalyser.statsOf(tree));
		}
		System.out.println(stats);

		 */
	}

}
