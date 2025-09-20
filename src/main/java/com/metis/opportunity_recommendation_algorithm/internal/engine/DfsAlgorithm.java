package com.metis.opportunity_recommendation_algorithm.internal.engine;

import com.metis.opportunity_recommendation_algorithm.internal.models.Edge;
import com.metis.opportunity_recommendation_algorithm.internal.models.KnowledgeGraph;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DfsAlgorithm {

    private static DfsAlgorithm instance;

    public static DfsAlgorithm getInstance() {
        if (instance == null) {
            instance = new DfsAlgorithm();
        }
        return instance;
    }

    /**
     * Executa uma travessia DFS completa, visitando todos os nós do grafo
     *
     * @return Conjunto de nós visitados
     */
    public Set<Node> dfsComplete(KnowledgeGraph graph) {
        Set<Node> visited = new HashSet<>();

        for (Node node : graph.getNodes()) {
            if (!visited.contains(node)) {
                dfsRecursive(graph, node, visited);
            }
        }

        return visited;
    }

    private void dfsRecursive(KnowledgeGraph graph, Node currentNode, Set<Node> visited) {
        visited.add(currentNode);

        List<Edge> edges = graph.getAdjacencyList().get(currentNode);
        if (edges != null) {
            for (Edge edge : edges) {
                Node neighbor = edge.getTarget();
                if (!visited.contains(neighbor)) {
                    dfsRecursive(graph, neighbor, visited);
                }
            }
        }
    }

    /**
     * Executa uma travessia DFS e retorna os nós na ordem de tempo de finalização
     *
     * @return Pilha com os nós em ordem decrescente de tempo de finalização
     */
    public Stack<Node> dfsWithFinishingTime(KnowledgeGraph graph) {
        Stack<Node> finishingTime = new Stack<>();
        Set<Node> visited = new HashSet<>();

        for (Node node : graph.getNodes()) {
            if (!visited.contains(node)) {
                dfsRecursiveWithFinishing(graph, node, visited, finishingTime);
            }
        }

        return finishingTime;
    }

    private void dfsRecursiveWithFinishing(KnowledgeGraph graph, Node currentNode, Set<Node> visited, Stack<Node> finishingTime) {
        visited.add(currentNode);

        List<Edge> edges = graph.getAdjacencyList().get(currentNode);
        if (edges != null) {
            for (Edge edge : edges) {
                Node neighbor = edge.getTarget();
                if (!visited.contains(neighbor)) {
                    dfsRecursiveWithFinishing(graph, neighbor, visited, finishingTime);
                }
            }
        }

        finishingTime.push(currentNode);
    }

    /**
     * Executa DFS a partir de um nó específico e adiciona os nós visitados a um conjunto de componentes
     *
     * @param component Conjunto para armazenar os nós conectados ao startNode
     */
    public void dfsForComponent(KnowledgeGraph graph, Node startNode, Set<Node> visited, Set<Node> component) {
        visited.add(startNode);
        component.add(startNode);

        List<Edge> edges = graph.getAdjacencyList().get(startNode);
        if (edges != null) {
            for (Edge edge : edges) {
                Node neighbor = edge.getTarget();
                if (!visited.contains(neighbor)) {
                    dfsForComponent(graph, neighbor, visited, component);
                }
            }
        }
    }

}
