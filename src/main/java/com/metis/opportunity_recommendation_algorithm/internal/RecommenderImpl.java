package com.metis.opportunity_recommendation_algorithm.internal;

import com.metis.opportunity_recommendation_algorithm.api.Opportunity;
import com.metis.opportunity_recommendation_algorithm.api.Recommender;
import com.metis.opportunity_recommendation_algorithm.api.exception.RecommendationException;
import com.metis.opportunity_recommendation_algorithm.internal.engine.RecommendationAlgorithm;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import com.metis.opportunity_recommendation_algorithm.internal.models.NodeType;
import com.metis.opportunity_recommendation_algorithm.internal.models.ScoredOpportunity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class RecommenderImpl implements Recommender {

    private final RecommendationAlgorithm recommendationAlgorithm;

    @Override
    public List<Opportunity> recommend(String studentId, int topN) throws RecommendationException {
        log.debug("Recommending opportunities for student {} with topN {}", studentId, topN);

        if (studentId == null || studentId.trim().isEmpty()) {
            throw new RecommendationException("Student ID cannot be null or empty");
        }

        if (topN < 0) {
            throw new RecommendationException("TopN cannot be negative");
        }

        try {
            List<ScoredOpportunity> scoredOpportunities = recommendationAlgorithm.recommend(studentId, topN);

            return scoredOpportunities
                    .stream()
                    .map(OpportunityMapper::mapToOpportunity)
                    .collect(Collectors.toList());

        } catch (IllegalArgumentException e) {
            log.warn("Student not found: {}", studentId);
            throw new RecommendationException("Student not found: " + studentId, e);
        } catch (Exception e) {
            log.error("Error recommending opportunities for student {}", studentId, e);
            throw new RecommendationException("Failed to recommend opportunities", e);
        }
    }

}