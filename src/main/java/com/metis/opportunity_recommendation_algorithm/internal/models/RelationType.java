package com.metis.opportunity_recommendation_algorithm.internal.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RelationType {

    POSSUI_HABILIDADE(1.0),

    TEM_INTERESSE_EM(1.0),

    REQUER_HABILIDADE(2.0),

    RELACIONADA_A_TEMA(1.5),
    ;

    private final double weight;

}
