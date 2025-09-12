package internal;

import lombok.Data;

import java.util.Objects;

@Data
public class Edge {

    private final Node target;
    private final RelationType type;

    public Edge(Node target, RelationType type) {
        this.target = Objects.requireNonNull(target, "Node target cannot be null");
        this.type = Objects.requireNonNull(type, "Relation type cannot be null");
    }

}
