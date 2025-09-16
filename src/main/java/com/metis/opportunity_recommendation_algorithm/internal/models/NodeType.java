package com.metis.opportunity_recommendation_algorithm.internal.models;

import java.util.Arrays;
import java.util.stream.Collectors;

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

    public static String listTypes() {
        return Arrays.stream(values())
                .map(NodeType::name)
                .collect(Collectors.joining(","));
    }

}
