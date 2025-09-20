package com.metis.opportunity_recommendation_algorithm.internal.models.response;

import com.metis.opportunity_recommendation_algorithm.internal.models.Node;

public record ScoredOpportunityResponse(Node opportunity, double score) {
}
