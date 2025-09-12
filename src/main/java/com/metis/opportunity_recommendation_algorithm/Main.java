package com.metis.opportunity_recommendation_algorithm;

import com.metis.opportunity_recommendation_algorithm.internal.engine.KnowledgeGraph;
import com.metis.opportunity_recommendation_algorithm.internal.engine.RecommendationAlgorithm;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import com.metis.opportunity_recommendation_algorithm.internal.models.NodeType;
import com.metis.opportunity_recommendation_algorithm.internal.models.RelationType;

public class Main {

    public static KnowledgeGraph graph() {
        KnowledgeGraph graph = new KnowledgeGraph();
        graph.addNode(new Node("aluno1", NodeType.STUDENT));
        graph.addNode(new Node("aluno2", NodeType.STUDENT));

        graph.addNode(new Node("hab_java", NodeType.SKILL));
        graph.addNode(new Node("hab_python", NodeType.SKILL));
        graph.addNode(new Node("hab_sql", NodeType.SKILL));

        graph.addNode(new Node("tema_ia", NodeType.THEME));
        graph.addNode(new Node("tema_dados", NodeType.THEME));

        graph.addNode(new Node("op1", NodeType.OPPORTUNITY));
        graph.addNode(new Node("op2", NodeType.OPPORTUNITY));

        graph.addEdge("aluno1", RelationType.HAS_SKILL, "hab_java");
        graph.addEdge("aluno1", RelationType.INTERESTED_IN, "tema_ia");

        graph.addEdge("aluno2", RelationType.HAS_SKILL, "hab_python");
        graph.addEdge("aluno2", RelationType.HAS_SKILL, "hab_sql");
        graph.addEdge("aluno2", RelationType.INTERESTED_IN, "tema_dados");

        graph.addEdge("op1", RelationType.REQUIRES_SKILL, "hab_java");
        graph.addEdge("op1", RelationType.REQUIRES_SKILL, "hab_python");
        graph.addEdge("op1", RelationType.RELATED_TO_THEME, "tema_ia");

        graph.addEdge("op2", RelationType.REQUIRES_SKILL, "hab_sql");
        graph.addEdge("op2", RelationType.RELATED_TO_THEME, "tema_dados");

        return graph;
    }

    public static void main(String[] args) {
        KnowledgeGraph graph = graph();
        RecommendationAlgorithm service = new RecommendationAlgorithm(graph);

        System.out.println("=== Sistema de Recomendação de Oportunidades ===");
        System.out.println();
        System.out.println("Pesos dos tipos de relacionamento:");
        System.out.println("- HAS_SKILL: " + RelationType.HAS_SKILL.getWeight());
        System.out.println("- INTERESTED_IN: " + RelationType.INTERESTED_IN.getWeight());
        System.out.println("- REQUIRES_SKILL: " + RelationType.REQUIRES_SKILL.getWeight());
        System.out.println("- RELATED_TO_THEME: " + RelationType.RELATED_TO_THEME.getWeight());
        System.out.println();

        System.out.println("Recomendações para Maria (Java + IA):");
        var mariaRecommendations = service.recommend("aluno1", 5);
        for (var scoredOpp : mariaRecommendations) {
            System.out.printf("- %s (Score: %.1f)%n",
                scoredOpp.getOpportunity().getId(),
                scoredOpp.getScore());
        }

        System.out.println();
        System.out.println("Cálculo do score para Maria:");
        System.out.println("- Oportunidade 1 (op1):");
        System.out.println("  * Requer Java: Maria possui Java → +" + RelationType.REQUIRES_SKILL.getWeight());
        System.out.println("  * Requer Python: Maria NÃO possui Python → +0");
        System.out.println("  * Relacionada a IA: Maria tem interesse em IA → +" + RelationType.RELATED_TO_THEME.getWeight());
        System.out.println("  * Score total: " + (RelationType.REQUIRES_SKILL.getWeight() + RelationType.RELATED_TO_THEME.getWeight()));

        System.out.println();
        System.out.println("Recomendações para João (Python + SQL + Dados):");
        var joaoRecommendations = service.recommend("aluno2", 5);
        for (var scoredOpp : joaoRecommendations) {
            System.out.printf("- %s (Score: %.1f)%n",
                scoredOpp.getOpportunity().getId(),
                scoredOpp.getScore());
        }
    }

}
