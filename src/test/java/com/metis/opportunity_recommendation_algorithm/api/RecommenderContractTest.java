package com.metis.opportunity_recommendation_algorithm.api;

import com.metis.opportunity_recommendation_algorithm.api.exception.RecommendationException;
import com.metis.opportunity_recommendation_algorithm.api.response.OpportunityResponse;
import com.metis.opportunity_recommendation_algorithm.internal.models.KnowledgeGraph;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import com.metis.opportunity_recommendation_algorithm.internal.models.enums.NodeType;
import com.metis.opportunity_recommendation_algorithm.internal.models.enums.RelationType;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class RecommenderContractTest {

    @Test
    void recommend_shouldReturnListOfOpportunities() {
        KnowledgeGraph graph = new KnowledgeGraph();
        Node student = new Node("student123", NodeType.STUDENT);
        Node skill = new Node("java", NodeType.SKILL);
        Node opportunity = new Node("job1", NodeType.OPPORTUNITY);

        opportunity.getProperties().put("description", "Java Developer Position");

        graph.addNode(student);
        graph.addNode(skill);
        graph.addNode(opportunity);
        graph.addEdge("student123", RelationType.HAS_SKILL, "java");
        graph.addEdge("job1", RelationType.REQUIRES_SKILL, "java");

        Recommender recommender = RecommenderFactory.create(graph);
        String studentId = "student123";
        int topN = 5;

        List<OpportunityResponse> result = recommender.recommend(studentId, topN);

        assertNotNull(result);
        assertTrue(result.size() <= topN);
        assertEquals(1, result.size());
        assertEquals("job1", result.get(0).id());
    }

    @Test
    void recommend_shouldHandleInvalidStudentId() {
        Recommender recommender = createRecommender();
        String invalidStudentId = "invalid";
        int topN = 3;

        assertThrows(RecommendationException.class, () -> {
            recommender.recommend(invalidStudentId, topN);
        });
    }

    @Test
    void recommend_shouldHandleTopNZero() {
        KnowledgeGraph graph = new KnowledgeGraph();
        Node student = new Node("student123", NodeType.STUDENT);
        Node skill = new Node("java", NodeType.SKILL);
        Node opportunity = new Node("job1", NodeType.OPPORTUNITY);

        opportunity.getProperties().put("description", "Java Developer Position");

        graph.addNode(student);
        graph.addNode(skill);
        graph.addNode(opportunity);
        graph.addEdge("student123", RelationType.HAS_SKILL, "java");
        graph.addEdge("job1", RelationType.REQUIRES_SKILL, "java");

        Recommender recommender = RecommenderFactory.create(graph);
        String studentId = "student123";
        int topN = 0;

        List<OpportunityResponse> result = recommender.recommend(studentId, topN);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    private Recommender createRecommender() {
        return RecommenderFactory.create();
    }
}