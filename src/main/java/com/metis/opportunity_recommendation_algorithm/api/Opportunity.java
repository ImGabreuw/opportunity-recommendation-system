package com.metis.opportunity_recommendation_algorithm.api;

import lombok.Value;

@Value
public class Opportunity {

    String id;

    String description;

    double relevanceScore;

}