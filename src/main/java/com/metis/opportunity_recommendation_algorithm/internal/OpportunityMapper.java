package com.metis.opportunity_recommendation_algorithm.internal;

import com.metis.opportunity_recommendation_algorithm.api.response.OpportunityResponse;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import com.metis.opportunity_recommendation_algorithm.internal.models.response.ScoredOpportunityResponse;

public class OpportunityMapper {

    public static OpportunityResponse mapToOpportunity(ScoredOpportunityResponse scoredOpportunityResponse) {
        Node node = scoredOpportunityResponse.opportunity();

        if (!node.isOpportunityNode()) {
            throw new IllegalArgumentException("Node is not an opportunity: " + node.getId());
        }

        String description = node.getProperty("description", null);
        double relevanceScore = scoredOpportunityResponse.score();

        return new OpportunityResponse(node.getId(), description, relevanceScore);
    }

}
