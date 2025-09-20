package com.metis.opportunity_recommendation_algorithm.api;

import com.metis.opportunity_recommendation_algorithm.api.response.OpportunityResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OpportunityResponseContractTest {

    @Test
    void opportunity_shouldBeImmutable() {
        String id = "opp123";
        String description = "Sample opportunity";
        double score = 0.85;

        OpportunityResponse opp = new OpportunityResponse(id, description, score);

        assertEquals(id, opp.id());
        assertEquals(description, opp.description());
        assertEquals(score, opp.relevanceScore());
    }

    @Test
    void opportunity_shouldHandleNullDescription() {
        String id = "opp123";
        String description = null;
        double score = 0.5;

        OpportunityResponse opp = new OpportunityResponse(id, description, score);

        assertEquals(id, opp.id());
        assertNull(opp.description());
        assertEquals(score, opp.relevanceScore());
    }

    @Test
    void opportunity_shouldImplementEqualsAndHashCode() {
        OpportunityResponse opp1 = new OpportunityResponse("opp123", "desc", 0.8);
        OpportunityResponse opp2 = new OpportunityResponse("opp123", "desc", 0.8);
        OpportunityResponse opp3 = new OpportunityResponse("opp456", "desc", 0.8);

        assertEquals(opp1, opp2);
        assertNotEquals(opp1, opp3);
        assertEquals(opp1.hashCode(), opp2.hashCode());
    }

    @Test
    void opportunity_shouldImplementToString() {
        OpportunityResponse opp = new OpportunityResponse("opp123", "Sample opp", 0.75);

        String str = opp.toString();

        assertNotNull(str);
        assertTrue(str.contains("opp123"));
        assertTrue(str.contains("Sample opp"));
        assertTrue(str.contains("0.75"));
    }
}