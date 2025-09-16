package com.metis.opportunity_recommendation_algorithm.internal.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RelationType {

    HAS_SKILL(1.0),

    INTERESTED_IN(1.0),

    REQUIRES_SKILL(2.0),

    RELATED_TO_THEME(1.5),
    ;

    private final double weight;

    public static RelationType fromName(String name) {
        for (RelationType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

}
