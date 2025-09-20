package com.metis.opportunity_recommendation_algorithm.internal.engine;

import com.metis.opportunity_recommendation_algorithm.internal.GraphUtils;
import com.metis.opportunity_recommendation_algorithm.internal.models.enums.DirectedGraphCategory;
import com.metis.opportunity_recommendation_algorithm.internal.models.KnowledgeGraph;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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

    /**
     * Classifica o grafo direcionado em uma das categorias C0, C1, C2 ou C3.
     *
     * @return Categoria do grafo direcionado {@see DirectedGraphCategory}
     */
    public DirectedGraphCategory categorize(KnowledgeGraph graph) {
        boolean isC3 = isC3(graph);
        if (isC3) {
            return DirectedGraphCategory.C3;
        }

        boolean isC1 = isC1(graph);
        if (isC1) {
            boolean isC2 = isC2(graph);
            if (isC2) {
                return DirectedGraphCategory.C2;
            }

            return DirectedGraphCategory.C1;
        }

        return DirectedGraphCategory.C0;
    }

    private boolean isC3(KnowledgeGraph graph) {
        KosarajuAlgorithm kosarajuAlgorithm = KosarajuAlgorithm.getInstance();
        Set<Set<Node>> scc = kosarajuAlgorithm.findStronglyConnectedComponents(graph);
        return scc.size() == 1;
    }

    private boolean isC1(KnowledgeGraph graph) {
        KnowledgeGraph undirectedGraph = GraphUtils.transformToUndirected(graph);

        DfsAlgorithm dfsAlgorithm = DfsAlgorithm.getInstance();
        Set<Node> visited = dfsAlgorithm.dfsComplete(undirectedGraph);
        return visited.size() == undirectedGraph.countNodes();
    }

    private boolean isC2(KnowledgeGraph graph) {
        KosarajuAlgorithm kosarajuAlgorithm = KosarajuAlgorithm.getInstance();
        KnowledgeGraph condensedGraph = kosarajuAlgorithm.getCondensedGraph(graph);

        if (condensedGraph.countNodes() <= 1) {
            return false; // C3
        }

        HamiltonianDfsAlgorithm hamiltonianAlgorithm = HamiltonianDfsAlgorithm.getInstance();
        return hamiltonianAlgorithm.hasHamiltonianPath(condensedGraph);
    }

}
