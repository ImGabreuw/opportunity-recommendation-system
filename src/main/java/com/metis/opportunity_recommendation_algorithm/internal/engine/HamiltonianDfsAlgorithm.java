package com.metis.opportunity_recommendation_algorithm.internal.engine;

import com.metis.opportunity_recommendation_algorithm.internal.models.Edge;
import com.metis.opportunity_recommendation_algorithm.internal.models.KnowledgeGraph;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HamiltonianDfsAlgorithm {

    private static HamiltonianDfsAlgorithm instance;

    public static HamiltonianDfsAlgorithm getInstance() {
        if (instance == null) {
            instance = new HamiltonianDfsAlgorithm();
        }
        return instance;
    }

    /**
     * Verifica se existe um caminho hamiltoniano no DAG (visita todos os vértices exatamente uma vez)
     */
    public boolean hasHamiltonianPath(KnowledgeGraph dag) {
        List<Node> nodes = dag.getNodes();

        for (Node startNode : nodes) {
            Set<Node> visited = new HashSet<>();
            if (dfsHamiltonianPath(dag, startNode, visited, nodes.size())) {
                return true;
            }
        }

        return false;
    }

    private boolean dfsHamiltonianPath(KnowledgeGraph dag, Node currentNode, Set<Node> visited, int totalNodes) {
        visited.add(currentNode);

        if (visited.size() == totalNodes) {
            return true;
        }

        List<Edge> edges = dag.getAdjacencyList().get(currentNode);
        if (edges != null) {
            for (Edge edge : edges) {
                Node neighbor = edge.getTarget();
                if (!visited.contains(neighbor)) {
                    if (dfsHamiltonianPath(dag, neighbor, visited, totalNodes)) {
                        return true;
                    }
                }
            }
        }

        visited.remove(currentNode);
        return false;
    }
}
