package com.cdcone.recipy.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RecipyResponseTest {
    /**
     * Method under test: default or parameterless constructor of {@link RecipyResponse}
     */
    @Test
    void testConstructor() {
        RecipyResponse actualRecipyResponse = new RecipyResponse("Data", "Not all who wander are lost");

        assertEquals("Data", actualRecipyResponse.getData());
        assertEquals("Not all who wander are lost", actualRecipyResponse.getMessage());
    }
}

