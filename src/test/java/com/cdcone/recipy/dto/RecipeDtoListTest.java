package com.cdcone.recipy.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RecipeDtoListTest {
    /**
     * Method under test: default or parameterless constructor of {@link RecipeDtoList}
     */
    @Test
    void testConstructor() {
        RecipeDtoList actualRecipeDtoList = new RecipeDtoList("Recipe Name", "The characteristics of someone or something",
                1, "JaneDoe");

        assertEquals("JaneDoe", actualRecipeDtoList.getAuthor());
        assertEquals(1, actualRecipeDtoList.getRecipeViews());
        assertEquals("Recipe Name", actualRecipeDtoList.getRecipeName());
        assertEquals("The characteristics of someone or something", actualRecipeDtoList.getDescription());
        assertEquals(-1, actualRecipeDtoList.getAuthorFollower());
    }
}

