package com.metis.opportunity_recommendation_algorithm.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.metis.opportunity_recommendation_algorithm.api.Opportunity;
import com.metis.opportunity_recommendation_algorithm.api.Recommender;
import com.metis.opportunity_recommendation_algorithm.api.RecommenderFactory;
import com.metis.opportunity_recommendation_algorithm.internal.engine.KnowledgeGraph;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import com.metis.opportunity_recommendation_algorithm.internal.models.NodeType;
import com.metis.opportunity_recommendation_algorithm.internal.models.RelationType;

public class RecommendationIntegrationTest {

    @Test
    void recommend_shouldReturnOpportunitiesBasedOnTags() {
        KnowledgeGraph graph = createSampleRecommendationGraph();
        Recommender recommender = RecommenderFactory.create(graph);

        List<Opportunity> result = recommender.recommend("student1", 10);

        assertNotNull(result);
        assertEquals(2, result.size());

        boolean hasJavaJob = result.stream().anyMatch(op -> op.getId().equals("java_job"));
        boolean hasPythonJob = result.stream().anyMatch(op -> op.getId().equals("python_job"));

        assertTrue(hasJavaJob, "Java job should be recommended");
        assertTrue(hasPythonJob, "Python job should be recommended");
    }

    @Test
    void recommend_shouldCalculateCorrectRelevanceScores() {
        KnowledgeGraph graph = createSampleRecommendationGraph();
        Recommender recommender = RecommenderFactory.create(graph);

        List<Opportunity> result = recommender.recommend("student1", 10);

        assertNotNull(result);
        assertEquals(2, result.size());

        for (Opportunity opp : result) {
            assertEquals(2.0, opp.getRelevanceScore(), 0.01);
        }
    }

    @Test
    void recommend_shouldHandleMultipleTagMatches() {
        KnowledgeGraph graph = createGraphWithMultipleMatches();
        Recommender recommender = RecommenderFactory.create(graph);

        List<Opportunity> result = recommender.recommend("student1", 10);

        assertNotNull(result);
        assertEquals(1, result.size());

        Opportunity opp = result.get(0);
        assertEquals(3.5, opp.getRelevanceScore(), 0.01);
    }

    private KnowledgeGraph createSampleRecommendationGraph() {
        KnowledgeGraph graph = new KnowledgeGraph();

        Node student = new Node("student1", NodeType.STUDENT);

        Node javaSkill = new Node("java", NodeType.SKILL);
        Node pythonSkill = new Node("python", NodeType.SKILL);

        Node javaJob = new Node("java_job", NodeType.OPPORTUNITY);
        Node pythonJob = new Node("python_job", NodeType.OPPORTUNITY);

        javaJob.setProperty("description", "Java Developer Position");
        pythonJob.setProperty("description", "Python Developer Position");

        graph.addNode(student);
        graph.addNode(javaSkill);
        graph.addNode(pythonSkill);
        graph.addNode(javaJob);
        graph.addNode(pythonJob);

        graph.addEdge("student1", RelationType.POSSUI_HABILIDADE, "java");
        graph.addEdge("student1", RelationType.POSSUI_HABILIDADE, "python");
        graph.addEdge("java_job", RelationType.REQUER_HABILIDADE, "java");
        graph.addEdge("python_job", RelationType.REQUER_HABILIDADE, "python");

        return graph;
    }

    private KnowledgeGraph createGraphWithMultipleMatches() {
        KnowledgeGraph graph = new KnowledgeGraph();

        Node student = new Node("student1", NodeType.STUDENT);

        Node javaSkill = new Node("java", NodeType.SKILL);
        Node aiTheme = new Node("ai", NodeType.THEME);

        Node aiJavaJob = new Node("ai_java_job", NodeType.OPPORTUNITY);
        aiJavaJob.getProperties().put("description", "AI Java Developer Position");

        graph.addNode(student);
        graph.addNode(javaSkill);
        graph.addNode(aiTheme);
        graph.addNode(aiJavaJob);

        graph.addEdge("student1", RelationType.POSSUI_HABILIDADE, "java");
        graph.addEdge("student1", RelationType.TEM_INTERESSE_EM, "ai");
        graph.addEdge("ai_java_job", RelationType.REQUER_HABILIDADE, "java");
        graph.addEdge("ai_java_job", RelationType.RELACIONADA_A_TEMA, "ai");

        return graph;
    }
}