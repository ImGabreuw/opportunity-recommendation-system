package com.metis.opportunity_recommendation_algorithm.internal;

public enum RelationType {

    /**
     * ALUNO -> HABILIDADE
     */
    POSSUI_HABILIDADE,

    /**
     * ALUNO -> TEMA
     */
    TEM_INTERESSE_EM,

    /**
     * OPORTUNIDADE -> HABILIDADE
     */
    REQUER_HABILIDADE,

    /**
     * OPORTUNIDADE -> TEMA
     */
    RELACIONADA_A_TEMA,
    ;

}
