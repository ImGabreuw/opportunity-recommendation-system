package com.metis.opportunity_recommendation_algorithm.internal;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Data
public class Node {

    private final String id;
    private final NodeType type;
    private final Map<String, Object> properties;

    public Node(String id, NodeType type) {
        this.id = Objects.requireNonNull(id, "Node id cannot be null");
        this.type = Objects.requireNonNull(type, "Node type cannot be null");
        this.properties = new HashMap<>();
    }

}
