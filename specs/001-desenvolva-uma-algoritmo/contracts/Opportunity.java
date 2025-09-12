package com.metis.opportunity_recommendation_algorithm.api;

import lombok.Value;

/**
 * Representa uma oportunidade extracurricular recomendada.
 * DTO imutável para exposição na API pública.
 */
@Value
public class Opportunity {

    /**
     * ID único da oportunidade.
     */
    String id;

    /**
     * Descrição da oportunidade (opcional).
     */
    String description;

    /**
     * Score de relevância calculado pelo algoritmo (0.0 a 1.0).
     */
    double relevanceScore;

}