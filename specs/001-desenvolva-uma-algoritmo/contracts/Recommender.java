package com.metis.opportunity_recommendation_algorithm.api;

import java.util.List;

/**
 * Interface principal para o motor de recomendação de oportunidades.
 * Implementa algoritmo content-based baseado em grafos.
 */
public interface Recommender {

    /**
     * Recomenda uma lista de oportunidades para o estudante identificado.
     *
     * @param studentId ID do estudante
     * @param topN número máximo de recomendações a retornar
     * @return lista de oportunidades ordenadas por relevância (maior para menor)
     * @throws RecommendationException se estudante não encontrado ou erro interno
     */
    List<Opportunity> recommend(String studentId, int topN);

}