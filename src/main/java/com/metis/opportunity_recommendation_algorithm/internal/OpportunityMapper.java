package com.metis.opportunity_recommendation_algorithm.internal;

import com.metis.opportunity_recommendation_algorithm.api.Opportunity;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import com.metis.opportunity_recommendation_algorithm.internal.models.ScoredOpportunity;

public class OpportunityMapper {

    public static Opportunity mapToOpportunity(ScoredOpportunity scoredOpportunity) {
        Node node = scoredOpportunity.getOpportunity();

        if (!node.isOpportunityNode()) {
            throw new IllegalArgumentException("Node is not an opportunity: " + node.getId());
        }

        String description = node.getProperty("description", null);
        double relevanceScore = scoredOpportunity.getScore();

        return new Opportunity(node.getId(), description, relevanceScore);
    }

}
