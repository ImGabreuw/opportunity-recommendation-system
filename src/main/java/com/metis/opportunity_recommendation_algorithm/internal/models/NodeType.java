package com.metis.opportunity_recommendation_algorithm.internal.models;

public enum NodeType {

    STUDENT,
    OPPORTUNITY,
    SKILL,
    THEME,
    ;

    public static NodeType fromName(String name) {
        for (NodeType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

}
