package com.metis.opportunity_recommendation_algorithm.internal.engine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.metis.opportunity_recommendation_algorithm.internal.models.KnowledgeGraph;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import com.metis.opportunity_recommendation_algorithm.internal.models.enums.RelationType;
import com.metis.opportunity_recommendation_algorithm.internal.models.response.ScoredOpportunityResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RecommendationAlgorithm {

    private final KnowledgeGraph graph;

    public List<ScoredOpportunityResponse> recommend(String studentId, int topN) {
        Node student = graph.getNode(studentId);

        if (student == null) {
            throw new IllegalArgumentException("Student not found");
        }

        Set<Node> studentSkills = graph.getNodesConnectedFrom(student, RelationType.HAS_SKILL);
        Set<Node> studentThemes = graph.getNodesConnectedFrom(student, RelationType.INTERESTED_IN);

        Set<Node> candidateOpportunities = new HashSet<>();
        for (Node habilidade : studentSkills) {
            candidateOpportunities.addAll(graph.getNodesConnectedTo(habilidade, RelationType.REQUIRES_SKILL));
        }
        for (Node tema : studentThemes) {
            candidateOpportunities.addAll(graph.getNodesConnectedTo(tema, RelationType.RELATED_TO_THEME));
        }

        List<ScoredOpportunityResponse> scoredOpportunities = new ArrayList<>();
        for (Node oportunidade : candidateOpportunities) {
            double score = 0.0;

            Set<Node> opportunitySkills = graph.getNodesConnectedFrom(oportunidade, RelationType.REQUIRES_SKILL);
            for (Node requiredSkill : opportunitySkills) {
                if (studentSkills.contains(requiredSkill)) {
                    score += RelationType.REQUIRES_SKILL.getWeight();
                }
            }

            Set<Node> opportunityThemes = graph.getNodesConnectedFrom(oportunidade, RelationType.RELATED_TO_THEME);
            for (Node relatedTheme : opportunityThemes) {
                if (studentThemes.contains(relatedTheme)) {
                    score += RelationType.RELATED_TO_THEME.getWeight();
                }
            }

            if (score > 0) {
                scoredOpportunities.add(new ScoredOpportunityResponse(oportunidade, score));
            }
        }

        return scoredOpportunities.stream()
                .sorted(Comparator.comparingDouble(ScoredOpportunityResponse::score).reversed())
                .limit(topN)
                .collect(Collectors.toList());
    }

}