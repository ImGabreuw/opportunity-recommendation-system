package com.metis.opportunity_recommendation_algorithm.internal.engine;

import com.metis.opportunity_recommendation_algorithm.internal.GraphUtils;
import com.metis.opportunity_recommendation_algorithm.internal.models.Edge;
import com.metis.opportunity_recommendation_algorithm.internal.models.KnowledgeGraph;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KosarajuAlgorithm {

    private static KosarajuAlgorithm instance;

    public static KosarajuAlgorithm getInstance() {
        if (instance == null) {
            instance = new KosarajuAlgorithm();
        }
        return instance;
    }

    /**
     * Encontra os componentes fortemente conexos num grafo dirigido usando o algoritmo de Kosaraju
     * @return Conjunto de conjuntos de nós, onde cada conjunto representa um componente fortemente conexo
     */
    public Set<Set<Node>> findStronglyConnectedComponents(KnowledgeGraph graph) {
        Set<Set<Node>> stronglyConnectedComponents = new HashSet<>();
        DfsAlgorithm dfsAlgorithm = DfsAlgorithm.getInstance();

        Stack<Node> finishingTime = dfsAlgorithm.dfsWithFinishingTime(graph);

        KnowledgeGraph transposedGraph = GraphUtils.transpose(graph);
        Set<Node> transposedVisited = new HashSet<>();

        while (!finishingTime.isEmpty()) {
            Node node = finishingTime.pop();

            if (!transposedVisited.contains(node)) {
                Set<Node> component = new HashSet<>();
                dfsAlgorithm.dfsForComponent(transposedGraph, node, transposedVisited, component);
                stronglyConnectedComponents.add(component);
            }
        }

        return stronglyConnectedComponents;
    }

    /**
     * Obtém o grafo condensado a partir do grafo original, onde cada componente fortemente conexo é representado como um "super-nó"
     * @return Grafo condensado
     */
    public KnowledgeGraph getCondensedGraph(KnowledgeGraph graph) {
        Set<Set<Node>> sccs = findStronglyConnectedComponents(graph);
        Map<Node, Node> componentMap = new HashMap<>();
        KnowledgeGraph condensedGraph = new KnowledgeGraph();

        // Create a mapping from each node to its component representative
        for (Set<Node> component : sccs) {
            Node representative = component.iterator().next();
            condensedGraph.addNode(representative);
            for (Node node : component) {
                componentMap.put(node, representative);
            }
        }

        // Add edges between components
        for (Node node : graph.getNodes()) {
            List<Edge> edges = graph.getAdjacencyList().get(node);
            if (edges != null) {
                for (Edge edge : edges) {
                    Node target = edge.getTarget();
                    Node sourceComponent = componentMap.get(node);
                    Node targetComponent = componentMap.get(target);

                    if (!sourceComponent.equals(targetComponent)) {
                        condensedGraph.addEdge(sourceComponent.getId(), edge.getType(), targetComponent.getId());
                    }
                }
            }
        }

        return condensedGraph;
    }
}
