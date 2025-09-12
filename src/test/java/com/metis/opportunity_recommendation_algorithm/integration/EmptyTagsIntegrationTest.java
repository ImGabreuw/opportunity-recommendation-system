package com.metis.opportunity_recommendation_algorithm.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.metis.opportunity_recommendation_algorithm.api.Opportunity;
import com.metis.opportunity_recommendation_algorithm.api.Recommender;
import com.metis.opportunity_recommendation_algorithm.api.RecommenderFactory;
import com.metis.opportunity_recommendation_algorithm.api.exception.RecommendationException;
import com.metis.opportunity_recommendation_algorithm.internal.engine.KnowledgeGraph;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import com.metis.opportunity_recommendation_algorithm.internal.models.NodeType;
import com.metis.opportunity_recommendation_algorithm.internal.models.RelationType;

public class EmptyTagsIntegrationTest {

    @Test
    void recommend_shouldReturnEmptyListWhenStudentHasNoSkills() {
        KnowledgeGraph graph = createGraphWithStudentWithoutSkills();
        Recommender recommender = RecommenderFactory.create(graph);

        List<Opportunity> result = recommender.recommend("student1", 10);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "Should return empty list when student has no skills");
    }

    @Test
    void recommend_shouldReturnEmptyListWhenNoMatchingOpportunities() {
        KnowledgeGraph graph = createGraphWithNoMatchingOpportunities();
        Recommender recommender = RecommenderFactory.create(graph);

        List<Opportunity> result = recommender.recommend("student1", 10);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "Should return empty list when no opportunities match student skills");
    }

    @Test
    void recommend_shouldHandleOpportunitiesWithoutRequiredSkills() {
        KnowledgeGraph graph = createGraphWithOpportunitiesWithoutSkills();
        Recommender recommender = RecommenderFactory.create(graph);

        List<Opportunity> result = recommender.recommend("student1", 10);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "Should return empty list when opportunities don't require skills");
    }

    @Test
    void recommend_shouldHandleEmptyGraph() {
        KnowledgeGraph graph = new KnowledgeGraph();
        Recommender recommender = RecommenderFactory.create(graph);

        assertThrows(RecommendationException.class,
                () -> recommender.recommend("student1", 10),
                "Should throw exception when student doesn't exist in empty graph");
    }

    private KnowledgeGraph createGraphWithStudentWithoutSkills() {
        KnowledgeGraph graph = new KnowledgeGraph();

        Node student = new Node("student1", NodeType.STUDENT);

        Node javaJob = new Node("java_job", NodeType.OPPORTUNITY);
        Node pythonJob = new Node("python_job", NodeType.OPPORTUNITY);

        Node javaSkill = new Node("java", NodeType.SKILL);
        Node pythonSkill = new Node("python", NodeType.SKILL);

        javaJob.getProperties().put("description", "Java Developer Position");
        pythonJob.getProperties().put("description", "Python Developer Position");

        graph.addNode(student);
        graph.addNode(javaJob);
        graph.addNode(pythonJob);
        graph.addNode(javaSkill);
        graph.addNode(pythonSkill);

        graph.addEdge("java_job", RelationType.REQUER_HABILIDADE, "java");
        graph.addEdge("python_job", RelationType.REQUER_HABILIDADE, "python");

        return graph;
    }

    private KnowledgeGraph createGraphWithNoMatchingOpportunities() {
        KnowledgeGraph graph = new KnowledgeGraph();

        Node student = new Node("student1", NodeType.STUDENT);

        Node javascriptSkill = new Node("javascript", NodeType.SKILL);
        Node reactSkill = new Node("react", NodeType.SKILL);

        Node javaJob = new Node("java_job", NodeType.OPPORTUNITY);
        Node pythonJob = new Node("python_job", NodeType.OPPORTUNITY);

        Node javaSkill = new Node("java", NodeType.SKILL);
        Node pythonSkill = new Node("python", NodeType.SKILL);

        javaJob.getProperties().put("description", "Java Developer Position");
        pythonJob.getProperties().put("description", "Python Developer Position");

        graph.addNode(student);
        graph.addNode(javascriptSkill);
        graph.addNode(reactSkill);
        graph.addNode(javaJob);
        graph.addNode(pythonJob);
        graph.addNode(javaSkill);
        graph.addNode(pythonSkill);

        graph.addEdge("student1", RelationType.POSSUI_HABILIDADE, "javascript");
        graph.addEdge("student1", RelationType.POSSUI_HABILIDADE, "react");
        graph.addEdge("java_job", RelationType.REQUER_HABILIDADE, "java");
        graph.addEdge("python_job", RelationType.REQUER_HABILIDADE, "python");

        return graph;
    }

    private KnowledgeGraph createGraphWithOpportunitiesWithoutSkills() {
        KnowledgeGraph graph = new KnowledgeGraph();

        Node student = new Node("student1", NodeType.STUDENT);

        Node javaSkill = new Node("java", NodeType.SKILL);
        Node pythonSkill = new Node("python", NodeType.SKILL);

        Node generalJob1 = new Node("general_job1", NodeType.OPPORTUNITY);
        Node generalJob2 = new Node("general_job2", NodeType.OPPORTUNITY);

        generalJob1.getProperties().put("description", "General Position 1");
        generalJob2.getProperties().put("description", "General Position 2");

        graph.addNode(student);
        graph.addNode(javaSkill);
        graph.addNode(pythonSkill);
        graph.addNode(generalJob1);
        graph.addNode(generalJob2);

        graph.addEdge("student1", RelationType.POSSUI_HABILIDADE, "java");
        graph.addEdge("student1", RelationType.POSSUI_HABILIDADE, "python");

        return graph;
    }
}
