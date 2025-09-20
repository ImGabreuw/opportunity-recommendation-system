package com.metis.opportunity_recommendation_algorithm.api;

import com.metis.opportunity_recommendation_algorithm.api.response.OpportunityResponse;

import java.util.List;

public interface Recommender {

    List<OpportunityResponse> recommend(String studentId, int topN);

}