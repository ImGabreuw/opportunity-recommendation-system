package com.metis.opportunity_recommendation_algorithm.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.metis.opportunity_recommendation_algorithm.api.Opportunity;
import com.metis.opportunity_recommendation_algorithm.api.Recommender;
import com.metis.opportunity_recommendation_algorithm.api.RecommenderFactory;
import com.metis.opportunity_recommendation_algorithm.internal.engine.KnowledgeGraph;
import com.metis.opportunity_recommendation_algorithm.internal.models.Node;
import com.metis.opportunity_recommendation_algorithm.internal.models.NodeType;
import com.metis.opportunity_recommendation_algorithm.internal.models.RelationType;

public class TopNLimitIntegrationTest {

    @Test
    void recommend_shouldRespectTopNLimit() {
        KnowledgeGraph graph = createGraphWithMultipleOpportunities();
        Recommender recommender = RecommenderFactory.create(graph);

        List<Opportunity> result = recommender.recommend("student1", 3);

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    void recommend_shouldReturnAllWhenTopNGreaterThanAvailable() {
        KnowledgeGraph graph = createGraphWithTwoOpportunities();
        Recommender recommender = RecommenderFactory.create(graph);

        List<Opportunity> result = recommender.recommend("student1", 5);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    private KnowledgeGraph createGraphWithMultipleOpportunities() {
        KnowledgeGraph graph = new KnowledgeGraph();

        Node student = new Node("student1", NodeType.STUDENT);
        Node skill = new Node("java", NodeType.SKILL);

        Node opp1 = new Node("job1", NodeType.OPPORTUNITY);
        Node opp2 = new Node("job2", NodeType.OPPORTUNITY);
        Node opp3 = new Node("job3", NodeType.OPPORTUNITY);
        Node opp4 = new Node("job4", NodeType.OPPORTUNITY);

        opp1.setProperty("description", "Java Developer 1");
        opp2.setProperty("description", "Java Developer 2");
        opp3.setProperty("description", "Java Developer 3");
        opp4.setProperty("description", "Java Developer 4");

        graph.addNode(student);
        graph.addNode(skill);
        graph.addNode(opp1);
        graph.addNode(opp2);
        graph.addNode(opp3);
        graph.addNode(opp4);

        graph.addEdge("student1", RelationType.POSSUI_HABILIDADE, "java");
        graph.addEdge("job1", RelationType.REQUER_HABILIDADE, "java");
        graph.addEdge("job2", RelationType.REQUER_HABILIDADE, "java");
        graph.addEdge("job3", RelationType.REQUER_HABILIDADE, "java");
        graph.addEdge("job4", RelationType.REQUER_HABILIDADE, "java");

        return graph;
    }

    private KnowledgeGraph createGraphWithTwoOpportunities() {
        KnowledgeGraph graph = new KnowledgeGraph();

        Node student = new Node("student1", NodeType.STUDENT);
        Node skill = new Node("python", NodeType.SKILL);

        Node opp1 = new Node("job1", NodeType.OPPORTUNITY);
        Node opp2 = new Node("job2", NodeType.OPPORTUNITY);

        opp1.setProperty("description", "Python Developer 1");
        opp2.setProperty("description", "Python Developer 2");

        graph.addNode(student);
        graph.addNode(skill);
        graph.addNode(opp1);
        graph.addNode(opp2);

        graph.addEdge("student1", RelationType.POSSUI_HABILIDADE, "python");
        graph.addEdge("job1", RelationType.REQUER_HABILIDADE, "python");
        graph.addEdge("job2", RelationType.REQUER_HABILIDADE, "python");

        return graph;
    }
}