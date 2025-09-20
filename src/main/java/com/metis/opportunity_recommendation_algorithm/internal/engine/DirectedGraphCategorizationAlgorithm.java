package com.metis.opportunity_recommendation_algorithm.internal.engine;

import com.metis.opportunity_recommendation_algorithm.internal.models.enums.DirectedGraphCategory;
import com.metis.opportunity_recommendation_algorithm.internal.models.Edge;
import com.metis.opportunity_recommendation_algorithm.internal.models.KnowledgeGraph;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DirectedGraphCategorizationAlgorithm {

    private static DirectedGraphCategorizationAlgorithm instance;

    public static DirectedGraphCategorizationAlgorithm getInstance() {
        if (instance == null) {
            instance = new DirectedGraphCategorizationAlgorithm();
        }
        return instance;
    }

    public DirectedGraphCategory categorize(KnowledgeGraph graph) {
        boolean isC3 = isC3(graph);
        if (isC3) {
            return DirectedGraphCategory.C3;
        }

        boolean isC1 = isC1(graph);
        if (isC1) {
            return DirectedGraphCategory.C1;
        }

        boolean isC2 = isC2(graph);
        if (isC2) {
            return DirectedGraphCategory.C2;
        }

        return DirectedGraphCategory.C0;
    }

    private boolean isC3(KnowledgeGraph graph) {
        KosarajuAlgorithm kosarajuAlgorithm = KosarajuAlgorithm.getInstance();
        Set<Set<Node>> scc = kosarajuAlgorithm.findStronglyConnectedComponents(graph);
        return scc.size() == 1;
    }

    private boolean isC1(KnowledgeGraph graph) {
        KnowledgeGraph undirectedGraph = transformToUndirected(graph);
        return hasVisitedAll(undirectedGraph);
    }

    private KnowledgeGraph transformToUndirected(KnowledgeGraph graph) {
        KnowledgeGraph undirectedGraph = new KnowledgeGraph();

        for (Node source : graph.getNodes()) {
            undirectedGraph.addNode(source);

            List<Edge> edges = graph.getAdjacencyList().get(source);

            if (edges == null) {
                continue;
            }

            String sourceId = source.getId();
            for (Edge entry : edges) {
                Node target = entry.getTarget();
                undirectedGraph.addNode(target);

                String targetId = target.getId();
                undirectedGraph.addEdge(sourceId, entry.getType(), targetId);
                undirectedGraph.addEdge(targetId, entry.getType(), sourceId);
            }
        }

        return undirectedGraph;
    }

    private boolean hasVisitedAll(KnowledgeGraph graph) {
        Set<Node> visited = dfsUnidirected(graph);
        return visited.size() == graph.countNodes();
    }

    private Set<Node> dfsUnidirected(KnowledgeGraph graph) {
        Set<Node> visited = new HashSet<>();

        for (Node node : graph.getNodes()) {
            if (!visited.contains(node)) {
                dfsUndirectedR(graph, node, visited);
            }
        }

        return visited;
    }

    private void dfsUndirectedR(KnowledgeGraph graph, Node currentNode, Set<Node> visited) {
        visited.add(currentNode);

        List<Edge> edges = graph.getAdjacencyList().get(currentNode);
        if (edges != null) {
            for (Edge edge : edges) {
                Node neighbor = edge.getTarget();
                if (!visited.contains(neighbor)) {
                    dfsUndirectedR(graph, neighbor, visited);
                }
            }
        }
    }

    private boolean isC2(KnowledgeGraph graph) {
        KosarajuAlgorithm kosarajuAlgorithm = KosarajuAlgorithm.getInstance();
        KnowledgeGraph condensedGraph = kosarajuAlgorithm.getCondensedGraph(graph);

        if (condensedGraph.countNodes() <= 1) {
            return false; // C3
        }

        return hasHamiltonianPath(condensedGraph);
    }

    /**
     * Verifica se existe um caminho hamiltoniano no DAG (visita todos os vértices exatamente uma vez)
     */
    private boolean hasHamiltonianPath(KnowledgeGraph dag) {
        List<Node> nodes = dag.getNodes();

        // Tenta começar de cada nó para encontrar um caminho hamiltoniano
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
