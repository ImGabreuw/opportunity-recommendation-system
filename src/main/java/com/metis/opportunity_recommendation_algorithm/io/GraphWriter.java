package com.metis.opportunity_recommendation_algorithm.io;

import com.metis.opportunity_recommendation_algorithm.internal.models.KnowledgeGraph;
import com.metis.opportunity_recommendation_algorithm.internal.models.Edge;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class GraphWriter {

    @Getter
    private final String filePath;

    @SneakyThrows
    public void write(KnowledgeGraph graph) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            int n = graph.countNodes();
            write(writer, "%d", n);

            for (Node node : graph.getNodes()) {
                write(writer, "%s %s", node.getId(), node.getType().name());
            }

            int m = graph.countEdges();
            write(writer, "%d", m);

            for (Map.Entry<Node, List<Edge>> entry : graph.getAdjacencyList().entrySet()) {
                Node source = entry.getKey();
                for (Edge edge : entry.getValue()) {
                    Node target = edge.getTarget();
                    write(writer, "%s %s %s", source.getId(), edge.getType().name(), target.getId());
                }
            }
        }
    }

    private void write(Writer writer, String mask, Object... values) throws IOException {
        writer.write(mask.formatted(values));
        writer.write("\n");
    }

}
