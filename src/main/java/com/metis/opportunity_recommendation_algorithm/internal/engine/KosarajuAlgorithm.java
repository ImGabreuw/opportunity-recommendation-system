package com.metis.opportunity_recommendation_algorithm.internal.engine;

import com.metis.opportunity_recommendation_algorithm.internal.models.Edge;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class KosarajuAlgorithm {

    private final KnowledgeGraph graph;

    /**
     * @return Pilha com os nós na ordem de finalização (descrecente)
     */
    private Stack<Node> dfs() {
        Stack<Node> finishingTime = new Stack<>();
        Set<Node> visited = new HashSet<>();

        for (Node node : graph.getNodes()) {
            if (!visited.contains(node)) {
                dfsR(node, visited, finishingTime);
            }
        }

        return finishingTime;
    }

    private void dfsR(Node currentNode, Set<Node> visited, Stack<Node> finishingTime) {
        visited.add(currentNode);

        List<Edge> edges = graph.getAdjacencyList().get(currentNode);
        if (edges != null) {
            for (Edge edge : edges) {
                Node neighbor = edge.getTarget();
                if (!visited.contains(neighbor)) {
                    dfsR(neighbor, visited, finishingTime);
                }
            }
        }

        finishingTime.push(currentNode);
    }

    private KnowledgeGraph transpose() {
        KnowledgeGraph transposedGraph = new KnowledgeGraph();

        for (Node node : graph.getNodes()) {
            transposedGraph.addNode(node);

            String sourceId = node.getId();

            List<Edge> edges = graph.getAdjacencyList().get(node);
            if (edges != null) {
                for (Edge edge : edges) {
                    Node targetId = edge.getTarget();
                    transposedGraph.addEdge(targetId.getId(), edge.getType(), sourceId);
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

    public Set<Set<Node>> findStronglyConnectedComponents() {
        Set<Set<Node>> stronglyConnectedComponents = new HashSet<>();

        Stack<Node> finishingTime = dfs();

        KnowledgeGraph transposedGraph = transpose();
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

    public Map<String, Set<Node>> getCondensedGraph() {
        Set<Set<Node>> sccs = findStronglyConnectedComponents();

        Map<String, Set<Node>> condensedGraph = new HashMap<>();
        int componentId = 0;

        for (Set<Node> component : sccs) {
            String id = "C" + componentId++;
            condensedGraph.put(id, component);
        }

        return condensedGraph;
    }

}
