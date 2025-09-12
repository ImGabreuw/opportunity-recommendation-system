package internal;

import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RecommendationService {

    private static final double SKILL_WEIGHT = 2.0;
    private static final double THEME_WEIGHT = 1.0;

    private final Graph graph;

    public List<Node> recommend(String studentId, int topN) {
        Node student = graph.getNode(studentId);

        if (student == null) {
            throw new IllegalArgumentException("Student not found");
        }

        // 1. Coletar Perfil do Aluno
        Set<Node> studentSkills = graph.getNodesConnectedFrom(student, RelationType.POSSUI_HABILIDADE);
        Set<Node> studentThemes = graph.getNodesConnectedFrom(student, RelationType.TEM_INTERESSE_EM);

        // 2. Encontrar Oportunidades Candidatas
        Set<Node> candidateOpportunities = new HashSet<>();
        for (Node habilidade : studentSkills) {
            candidateOpportunities.addAll(graph.getNodesConnectedTo(habilidade, RelationType.REQUER_HABILIDADE));
        }
        for (Node tema : studentThemes) {
            candidateOpportunities.addAll(graph.getNodesConnectedTo(tema, RelationType.RELACIONADA_A_TEMA));
        }

        // 3. Calcular Score de cada Oportunidade
        Map<Node, Double> scores = new HashMap<>();
        for (Node oportunidade : candidateOpportunities) {
            double score = 0.0;

            Set<Node> opportunitySkills = graph.getNodesConnectedFrom(oportunidade, RelationType.REQUER_HABILIDADE);
            for (Node requiredSkill : opportunitySkills) {
                if (studentSkills.contains(requiredSkill)) {
                    score += SKILL_WEIGHT;
                }
            }

            Set<Node> opportunityThemes = graph.getNodesConnectedFrom(oportunidade, RelationType.RELACIONADA_A_TEMA);
            for (Node relatedTheme : opportunityThemes) {
                if (studentThemes.contains(relatedTheme)) {
                    score += THEME_WEIGHT;
                }

                if (score > 0) {
                    scores.put(oportunidade, score);
                }
            }
        }

        return scores.entrySet().stream()
                .sorted(Map.Entry.<Node, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .limit(topN)
                .collect(Collectors.toList());
    }

}