package com.metis.opportunity_recommendation_algorithm.api;

import java.util.List;

public interface Recommender {

    List<Opportunity> recommend(String studentId, int topN);

}