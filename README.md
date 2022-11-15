Le projet est écrit pour Java 18 en mode `feature preview` ou Java 19. Le moteur de production utilisé est Gradle. Il contient initialement deux tâches ```run``` implémentées, l'une permet de lancer une application avec sortie en console, et l'autre une interface graphique permettant de dessiner un graphe.

Le projet comprend de nombreuses classes, mais vous n'avez vraiment besoin que de celles-ci :

- ```graph.UndirectedGraph``` (graphes non-orientés)
- ```graph.Edge``` (arêtes)
- ```graph.Main``` (pour réaliser une application en console)
- ```visualizer.Controller``` (pour réaliser une application en interface graphique)

Le package `randomtree` est prévu pour y placer les algorithmes de génération d'arbres couvrants aléatoires que vous écrirez.

```Main``` et ```Controller``` contiennent des exemples qui devraient être facile à adapter pour vos algorithmes, du moment que vos algorithmes retournent un ensemble d'arêtes (de type ```Set<Edge>```).


### Package ```graph``` :

- ```UndirectedGraph``` permet de représenter les graphes non-orientés, et dispose de méthodes pour ajouter des arêtes et itérer sur les arêtes, les sommets, les voisins d'un sommet.
- ```Edge``` permet de représenter une arête d'un graphe, comme paire de sommets.
- ```Main``` est une petite application, pouvant être exécuté via la tâche gradle ```runMain```. Vous pouvez y modifier les instructions à votre guise.
- ```EmbeddedGraph``` représente un graphe avec une fonction de placement des sommets dans le plan cartésien.
- ```generators``` est un package contenant plusieurs générateurs de graphes fixes ou aléatoires.
- ```rootedtrees``` est un package permettant d'analyser divers paramètres sur les arbres enracinés, comme la hauteur, la distribution de degré, etc. Un exemple d'utilisation est donné dans ```graph.Main```.
- ```search``` est un package contenant un algorithme de parcours de graphe, dont la classe principale est ```Search``` et contient aussi un test de connexité pour les graphes non-orientés.


### Package ```visualizer```

- ```Controller``` contient le contrôleur de la fenêtre graphique.
- ```DrawingParameters``` est une interface permettant de définir des paramètres influant sur le dessin des graphes. Elle a trois implémentations déjà écrites (```BlackDrawingParameter```, ```GrayDrawingParameter```, ```RainbowDrawingParameter```).
- ```Embedding``` est une interface définissant les plongements de graphe dans le plan cartésien, c'est-à-dire les fonctions des sommets vers les points du plan.
- ```EdgesDrawer``` est une classe réalisant le dessin d'un ensemble d'arêtes sur un canvas graphique.
- ```RandomTreeApplication``` contient le ```main``` de l'application en interface graphique. Cette classe ne doit pas être modifiée.

### Package ```Helpers```
- ```Iterations``` contient des méthodes pour manipuler des ```Iterable``` et ```Iterator```.
- ```Point``` définit un point 2D en coordonnée cartésienne.
