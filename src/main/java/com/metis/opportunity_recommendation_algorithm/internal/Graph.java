package com.metis.opportunity_recommendation_algorithm.internal;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Graph {

    private final Map<String, Node> nodes = new HashMap<>();
    private final Map<Node, List<Edge>> adjacencyList = new HashMap<>();

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    public Node getNode(String id) {
        return nodes.get(id);
    }

    public void addEdge(String sourceId, RelationType type, String targetId) {
        Node sourceNode = getNode(sourceId);
        Node targetNode = getNode(targetId);

        if (sourceNode != null && targetNode != null) {
            Edge edge = new Edge(targetNode, type);
            adjacencyList.get(sourceNode).add(edge);
        } else {
            throw new IllegalArgumentException("Source or target node not found");
        }
    }

    /**
     * Retorna todos os nós conectados A PARTIR de um nó de origem com um tipo de relação específico.
     * Ex: getNodesConnectedFrom(alunoNode, POSSUI_HABILIDADE) -> retorna lista de Habilidades
     */
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

    /**
     * Retorna todos os nós que se conectam A um nó de destino com um tipo de relação específico.
     * Ex: getNodesConnectedTo(habilidadeNode, REQUER_HABILIDADE) -> retorna lista de Oportunidades
     */
    public Set<Node> getNodesConnectedTo(Node target, RelationType type) {
        Set<Node> result = new HashSet<>();

        for (Map.Entry<Node, List<Edge>> entry : adjacencyList.entrySet()) {
            for (Edge edge : entry.getValue()) {
                if (edge.getTarget().equals(target) && edge.getType() == type) {
                    result.add(entry.getKey());
                }
            }
        }

        return result;
    }

}
