package com.metis.opportunity_recommendation_algorithm.internal.engine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import com.metis.opportunity_recommendation_algorithm.internal.models.RelationType;
import com.metis.opportunity_recommendation_algorithm.internal.models.ScoredOpportunity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecommendationAlgorithm {

    private final KnowledgeGraph graph;

    public List<ScoredOpportunity> recommend(String studentId, int topN) {
        Node student = graph.getNode(studentId);

        if (student == null) {
            throw new IllegalArgumentException("Student not found");
        }

        Set<Node> studentSkills = graph.getNodesConnectedFrom(student, RelationType.POSSUI_HABILIDADE);
        Set<Node> studentThemes = graph.getNodesConnectedFrom(student, RelationType.TEM_INTERESSE_EM);

        Set<Node> candidateOpportunities = new HashSet<>();
        for (Node habilidade : studentSkills) {
            candidateOpportunities.addAll(graph.getNodesConnectedTo(habilidade, RelationType.REQUER_HABILIDADE));
        }
        for (Node tema : studentThemes) {
            candidateOpportunities.addAll(graph.getNodesConnectedTo(tema, RelationType.RELACIONADA_A_TEMA));
        }

        List<ScoredOpportunity> scoredOpportunities = new ArrayList<>();
        for (Node oportunidade : candidateOpportunities) {
            double score = 0.0;

            Set<Node> opportunitySkills = graph.getNodesConnectedFrom(oportunidade, RelationType.REQUER_HABILIDADE);
            for (Node requiredSkill : opportunitySkills) {
                if (studentSkills.contains(requiredSkill)) {
                    score += RelationType.REQUER_HABILIDADE.getWeight();
                }
            }

            Set<Node> opportunityThemes = graph.getNodesConnectedFrom(oportunidade, RelationType.RELACIONADA_A_TEMA);
            for (Node relatedTheme : opportunityThemes) {
                if (studentThemes.contains(relatedTheme)) {
                    score += RelationType.RELACIONADA_A_TEMA.getWeight();
                }
            }

            if (score > 0) {
                scoredOpportunities.add(new ScoredOpportunity(oportunidade, score));
            }
        }

        return scoredOpportunities.stream()
                .sorted(Comparator.comparingDouble(ScoredOpportunity::getScore).reversed())
                .limit(topN)
                .collect(Collectors.toList());
    }

}