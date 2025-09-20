package com.metis.opportunity_recommendation_algorithm.internal.engine;

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
     * @return Pilha com os nós na ordem de finalização (descrecente)
     */
    private Stack<Node> dfs(KnowledgeGraph graph) {
        Stack<Node> finishingTime = new Stack<>();
        Set<Node> visited = new HashSet<>();

        for (Node node : graph.getNodes()) {
            if (!visited.contains(node)) {
                dfsR(graph, node, visited, finishingTime);
            }
        }

        return finishingTime;
    }

    private void dfsR(KnowledgeGraph graph, Node currentNode, Set<Node> visited, Stack<Node> finishingTime) {
        visited.add(currentNode);

        List<Edge> edges = graph.getAdjacencyList().get(currentNode);
        if (edges != null) {
            for (Edge edge : edges) {
                Node neighbor = edge.getTarget();
                if (!visited.contains(neighbor)) {
                    dfsR(graph, neighbor, visited, finishingTime);
                }
            }
        }

        finishingTime.push(currentNode);
    }

    private KnowledgeGraph transpose(KnowledgeGraph graph) {
        KnowledgeGraph transposedGraph = new KnowledgeGraph();

        for (Node source : graph.getNodes()) {
            transposedGraph.addNode(source);

            List<Edge> edges = graph.getAdjacencyList().get(source);
            if (edges != null) {
                String sourceId = source.getId();
                for (Edge edge : edges) {
                    Node target = edge.getTarget();
                    transposedGraph.addNode(target);

                    transposedGraph.addEdge(target.getId(), edge.getType(), sourceId);
                }
            }
        }

        return transposedGraph;
    }

    private void dsfOnTransposed(KnowledgeGraph transposedGraph, Node node, Set<Node> visited, Set<Node> components) {
        visited.add(node);
        components.add(node);

        List<Edge> edges = transposedGraph.getAdjacencyList().get(node);
        if (edges != null) {
            for (Edge edge : edges) {
                Node neighbor = edge.getTarget();
                if (!visited.contains(neighbor)) {
                    dsfOnTransposed(transposedGraph, neighbor, visited, components);
                }
            }
        }
    }

    public Set<Set<Node>> findStronglyConnectedComponents(KnowledgeGraph graph) {
        Set<Set<Node>> stronglyConnectedComponents = new HashSet<>();

        Stack<Node> finishingTime = dfs(graph);

        KnowledgeGraph transposedGraph = transpose(graph);
        Set<Node> transposedVisited = new HashSet<>();

        while (!finishingTime.isEmpty()) {
            Node node = finishingTime.pop();

            if (!transposedVisited.contains(node)) {
                Set<Node> component = new HashSet<>();
                dsfOnTransposed(transposedGraph, node, transposedVisited, component);
                stronglyConnectedComponents.add(component);
            }
        }

        return stronglyConnectedComponents;
    }

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
