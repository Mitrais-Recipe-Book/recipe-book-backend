package com.cdcone.recipy.dto;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

class RecipeDtoListTest {
    /**
     * Method under test: default or parameterless constructor of {@link RecipeDtoList}
     */
    @Test
    void testConstructor() {
        Byte[] byteArray = new Byte[]{'A'};
        Byte[] byteArray1 = new Byte[]{'A'};
        RecipeDtoList actualRecipeDtoList = new RecipeDtoList("Recipe Name", byteArray,
                "The characteristics of someone or something", 1, "JaneDoe", byteArray1);

        assertEquals("JaneDoe", actualRecipeDtoList.getAuthor());
        assertEquals(1, actualRecipeDtoList.getRecipeViews());
        assertEquals("Recipe Name", actualRecipeDtoList.getRecipeName());
        Byte[] recipeImage = actualRecipeDtoList.getRecipeImage();
        assertEquals(1, recipeImage.length);
        assertArrayEquals(new Byte[]{'A'}, recipeImage);
        //assertEquals('A', recipeImage[0]);
        assertEquals("The characteristics of someone or something", actualRecipeDtoList.getDescription());
        Byte[] authorImage = actualRecipeDtoList.getAuthorImage();
        assertEquals(1, authorImage.length);
        assertArrayEquals(new Byte[]{'A'}, authorImage);
        //assertEquals('A', authorImage[0]);
        assertEquals(-1, actualRecipeDtoList.getAuthorFollower());
        assertSame(recipeImage, byteArray);
        assertSame(authorImage, byteArray1);
    }
}

