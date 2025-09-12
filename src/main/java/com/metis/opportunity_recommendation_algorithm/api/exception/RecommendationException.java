package com.metis.opportunity_recommendation_algorithm.api.exception;

public class RecommendationException extends RuntimeException {

    public RecommendationException(String message) {
        super(message);
    }

    public RecommendationException(String message, Throwable cause) {
        super(message, cause);
    }

}