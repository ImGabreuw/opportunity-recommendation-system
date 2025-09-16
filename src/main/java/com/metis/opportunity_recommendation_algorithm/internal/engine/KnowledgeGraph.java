package com.metis.opportunity_recommendation_algorithm.internal.engine;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

import com.metis.opportunity_recommendation_algorithm.internal.models.Edge;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import com.metis.opportunity_recommendation_algorithm.internal.models.RelationType;

@Slf4j
public class KnowledgeGraph {

    private final Map<String, Node> nodes = new HashMap<>();

    @Getter
    private final Map<Node, List<Edge>> adjacencyList = new HashMap<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Node, List<Edge>> entry : adjacencyList.entrySet()) {
            Node source = entry.getKey();

            List<Edge> edges = entry.getValue();
            for (Edge edge : edges) {
                String edgeStr = "%s --[%s]--> %s".formatted(
                        source.getId(),
                        edge.getType().name(),
                        edge.getTarget().getId());
                sb.append(edgeStr).append("\n");
            }
        }
        return sb.toString();
    }

    public List<Node> getNodes() {
        return new ArrayList<>(nodes.values());
    }

    public Node getNode(String id) {
        return nodes.get(id);
    }

    public Set<Node> getNodesConnectedFrom(Node source, RelationType type) {
        if (!adjacencyList.containsKey(source)) {
            return Collections.emptySet();
        }

        return adjacencyList.get(source)
                .stream()
                .filter(edge -> edge.getType() == type)
                .map(Edge::getTarget)
                .collect(Collectors.toSet());
    }

    public Set<Node> getNodesConnectedTo(Node target, RelationType type) {
        Set<Node> result = new HashSet<>();

        for (Map.Entry<Node, List<Edge>> entry : adjacencyList.entrySet()) {
            for (Edge edge : entry.getValue()) {
                if (edge.isConnectingTo(target, type)) {
                    result.add(entry.getKey());
                }
            }
        }

        return result;
    }

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    public void removeNode(String id) {
        Node node = getNode(id);
        if (node == null) {
            throw new IllegalArgumentException("Node not found");
        }

        nodes.remove(id);
        adjacencyList.remove(node);

        // Remove edges connecting to this node
        for (List<Edge> edges : adjacencyList.values()) {
            edges.removeIf(edge -> edge.getTarget().equals(node));
        }
    }

    public int countNodes() {
        return nodes.size();
    }

    public void addEdge(String sourceId, RelationType type, String targetId) {
        Node sourceNode = getNode(sourceId);
        Node targetNode = getNode(targetId);

        if (sourceNode == null || targetNode == null) {
            throw new IllegalArgumentException("Source or target node not found");
        }

        Edge edge = new Edge(targetNode, type);
        adjacencyList.get(sourceNode).add(edge);
    }

    public void removeEdge(String sourceId, RelationType type, String targetId) {
        Node sourceNode = getNode(sourceId);
        Node targetNode = getNode(targetId);

        if (sourceNode == null || targetNode == null) {
            throw new IllegalArgumentException("Source or target node not found");
        }

        List<Edge> edges = adjacencyList.get(sourceNode);
        if (edges != null) {
            edges.removeIf(edge -> edge.isConnectingTo(targetNode, type));
        }
    }

    public int countEdges() {
        return adjacencyList.values().stream().mapToInt(List::size).sum();
    }

}
