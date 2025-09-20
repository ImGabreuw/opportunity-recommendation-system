package com.metis.opportunity_recommendation_algorithm.io;

import com.metis.opportunity_recommendation_algorithm.internal.models.KnowledgeGraph;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import com.metis.opportunity_recommendation_algorithm.internal.models.enums.NodeType;
import com.metis.opportunity_recommendation_algorithm.internal.models.enums.RelationType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
public class GraphReader {

    @Getter
    private final String filePath;

    @SneakyThrows
    public KnowledgeGraph read() {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            throw new IllegalArgumentException("File does not exist: " + filePath);
        }

        int n = 0, m = 0;

        int linePosition = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            KnowledgeGraph graph = new KnowledgeGraph();

            String line;
            while ((line = reader.readLine()) != null) {

                if (linePosition == 0) {
                    n = Integer.parseInt(line);
                }

                if (linePosition > 0 && linePosition <= n) {
                    String[] parts = line.split(" ");

                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Invalid node format at line " + (linePosition + 1) + ": <ID> <Tipo do Vértice>");
                    }

                    String nodeId = parts[0];
                    NodeType nodeType = NodeType.fromName(parts[1]);

                    if (nodeType == null) {
                        throw new IllegalArgumentException("Unknown node type at line " + (linePosition + 1) + ": " + parts[1]);
                    }

                    graph.addNode(new Node(nodeId, nodeType));
                }

                if (linePosition == n + 1) {
                    m = Integer.parseInt(line);
                }

                if (linePosition > n + 1 && linePosition <= n + 1 + m) {
                    String[] parts = line.split(" ");

                    if (parts.length != 3) {
                        throw new IllegalArgumentException("Invalid edge format at line " + (linePosition + 1) + ": <ID do Vértice de Origem> <ID do Vértice de Destino> <Tipo da Relação>");
                    }

                    String sourceId = parts[0];
                    RelationType relationType = RelationType.fromName(parts[1]);
                    String targetId = parts[2];

                    if (relationType == null) {
                        throw new IllegalArgumentException("Unknown relation type at line " + (linePosition + 1) + ": " + parts[2]);
                    }

                    Node sourceNode = graph.getNode(sourceId);

                    if (sourceNode == null) {
                        throw new IllegalArgumentException("Source node not found at line " + (linePosition + 1) + ": " + sourceId);
                    }

                    Node targetNode = graph.getNode(targetId);

                    if (targetNode == null) {
                        throw new IllegalArgumentException("Target node not found at line " + (linePosition + 1) + ": " + targetId);
                    }

                    graph.addEdge(sourceId, relationType, targetId);
                }

                linePosition++;
            }

            return graph;
        }
    }

}
