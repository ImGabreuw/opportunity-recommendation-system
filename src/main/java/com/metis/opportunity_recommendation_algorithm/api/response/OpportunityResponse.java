package com.metis.opportunity_recommendation_algorithm.api.response;

public record OpportunityResponse(String id, String description, double relevanceScore) {
}