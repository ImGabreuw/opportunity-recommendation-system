package com.metis.opportunity_recommendation_algorithm.api;

import com.metis.opportunity_recommendation_algorithm.internal.RecommenderImpl;
import com.metis.opportunity_recommendation_algorithm.internal.models.KnowledgeGraph;
import com.metis.opportunity_recommendation_algorithm.internal.engine.RecommendationAlgorithm;

public final class RecommenderFactory {

    private RecommenderFactory() {
    }

    public static Recommender create() {
        KnowledgeGraph graph = new KnowledgeGraph();
        RecommendationAlgorithm recommendationService = new RecommendationAlgorithm(graph);
        return new RecommenderImpl(recommendationService);
    }

    public static Recommender create(KnowledgeGraph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("Graph cannot be null");
        }
        RecommendationAlgorithm recommendationService = new RecommendationAlgorithm(graph);
        return new RecommenderImpl(recommendationService);
    }
}