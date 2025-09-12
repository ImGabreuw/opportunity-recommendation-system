package com.metis.opportunity_recommendation_algorithm.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

public class RecommenderImplTest {

    @Test
    void recommend_shouldWorkWithPopulatedGraph() {
        KnowledgeGraph graph = new KnowledgeGraph();

        Node student = new Node("student1", NodeType.STUDENT);
        Node skill1 = new Node("java", NodeType.SKILL);
        Node skill2 = new Node("python", NodeType.SKILL);
        Node opportunity1 = new Node("job1", NodeType.OPPORTUNITY);
        Node opportunity2 = new Node("job2", NodeType.OPPORTUNITY);

        opportunity1.setProperty("description", "Java Developer Position");
        opportunity2.setProperty("description", "Python Developer Position");

        graph.addNode(student);
        graph.addNode(skill1);
        graph.addNode(skill2);
        graph.addNode(opportunity1);
        graph.addNode(opportunity2);

        graph.addEdge("student1", RelationType.HAS_SKILL, "java");
        graph.addEdge("student1", RelationType.HAS_SKILL, "python");
        graph.addEdge("job1", RelationType.REQUIRES_SKILL, "java");
        graph.addEdge("job2", RelationType.REQUIRES_SKILL, "python");

        Recommender recommender = RecommenderFactory.create(graph);

        List<Opportunity> result = recommender.recommend("student1", 5);

        assertNotNull(result);
        assertEquals(2, result.size());

        assertTrue(result.stream().anyMatch(op -> op.getId().equals("job1")));
        assertTrue(result.stream().anyMatch(op -> op.getId().equals("job2")));
    }

    @Test
    void recommend_shouldHandleEmptyGraph() {
        Recommender recommender = RecommenderFactory.create();

        assertThrows(RecommendationException.class, () -> {
            recommender.recommend("nonexistent", 5);
        });
    }

    @Test
    void recommend_shouldHandleTopNLimit() {
        KnowledgeGraph graph = new KnowledgeGraph();

        Node student = new Node("student1", NodeType.STUDENT);
        Node skill1 = new Node("java", NodeType.SKILL);
        Node opportunity1 = new Node("job1", NodeType.OPPORTUNITY);
        Node opportunity2 = new Node("job2", NodeType.OPPORTUNITY);
        Node opportunity3 = new Node("job3", NodeType.OPPORTUNITY);

        opportunity1.setProperty("description", "Job 1");
        opportunity2.setProperty("description", "Job 2");
        opportunity3.setProperty("description", "Job 3");

        graph.addNode(student);
        graph.addNode(skill1);
        graph.addNode(opportunity1);
        graph.addNode(opportunity2);
        graph.addNode(opportunity3);

        graph.addEdge("student1", RelationType.HAS_SKILL, "java");
        graph.addEdge("job1", RelationType.REQUIRES_SKILL, "java");
        graph.addEdge("job2", RelationType.REQUIRES_SKILL, "java");
        graph.addEdge("job3", RelationType.REQUIRES_SKILL, "java");

        Recommender recommender = RecommenderFactory.create(graph);

        List<Opportunity> result = recommender.recommend("student1", 2);

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}