package com.metis.opportunity_recommendation_algorithm.internal.models;

import lombok.Value;

@Value
public class ScoredOpportunity {
    Node opportunity;
    double score;
}
