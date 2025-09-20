package com.metis.opportunity_recommendation_algorithm.internal;

import com.metis.opportunity_recommendation_algorithm.internal.models.Edge;
import com.metis.opportunity_recommendation_algorithm.internal.models.KnowledgeGraph;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GraphUtils {

    /**
     * Transforma um grafo direcionado em um grafo não direcionado adicionando arestas bidirecionais
     * @return Um KnowledgeGraph não direcionado
     */
    public static KnowledgeGraph transformToUndirected(KnowledgeGraph graph) {
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

    /**
     * Cria um grafo transposto (inverte a direção de todas as arestas)
     * @return Um KnowledgeGraph transposto
     */
    public static KnowledgeGraph transpose(KnowledgeGraph graph) {
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
}
