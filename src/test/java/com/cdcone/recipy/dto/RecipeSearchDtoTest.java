package com.cdcone.recipy.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class RecipeSearchDtoTest {
    /**
     * Method under test: {@link RecipeSearchDto#canEqual(Object)}
     */
    @Test
    void testCanEqual() {
        assertFalse((new RecipeSearchDto(3, 1, "JaneDoe", new HashSet<>())).canEqual("Other"));
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link RecipeSearchDto#RecipeSearchDto(int, int, String, Set)}
     *   <li>{@link RecipeSearchDto#setAuthor(String)}
     *   <li>{@link RecipeSearchDto#setPage(int)}
     *   <li>{@link RecipeSearchDto#setSize(int)}
     *   <li>{@link RecipeSearchDto#setTags(Set)}
     * </ul>
     */
    @Test
    void testConstructor() {
        RecipeSearchDto actualRecipeSearchDto = new RecipeSearchDto(3, 1, "JaneDoe", new HashSet<>());
        actualRecipeSearchDto.setAuthor("JaneDoe");
        actualRecipeSearchDto.setPage(1);
        actualRecipeSearchDto.setSize(3);
        HashSet<String> stringSet = new HashSet<>();
        actualRecipeSearchDto.setTags(stringSet);
        assertEquals("JaneDoe", actualRecipeSearchDto.getAuthor());
        assertEquals(1, actualRecipeSearchDto.getPage());
        assertEquals(3, actualRecipeSearchDto.getSize());
        assertSame(stringSet, actualRecipeSearchDto.getTags());
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals() {
        assertNotEquals(new RecipeSearchDto(3, 1, "JaneDoe", new HashSet<>()), null);
        assertNotEquals(new RecipeSearchDto(3, 1, "JaneDoe", new HashSet<>()), "Different type to RecipeSearchDto");
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals2() {
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto(3, 1, "JaneDoe", new HashSet<>());
        assertEquals(recipeSearchDto, recipeSearchDto);
        int expectedHashCodeResult = recipeSearchDto.hashCode();
        assertEquals(expectedHashCodeResult, recipeSearchDto.hashCode());
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals3() {
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto(3, 1, "JaneDoe", new HashSet<>());
        RecipeSearchDto recipeSearchDto1 = new RecipeSearchDto(3, 1, "JaneDoe", new HashSet<>());

        assertEquals(recipeSearchDto, recipeSearchDto1);
        int expectedHashCodeResult = recipeSearchDto.hashCode();
        assertEquals(expectedHashCodeResult, recipeSearchDto1.hashCode());
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals4() {
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto(1, 1, "JaneDoe", new HashSet<>());
        assertNotEquals(recipeSearchDto, new RecipeSearchDto(3, 1, "JaneDoe", new HashSet<>()));
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals5() {
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto(3, 3, "JaneDoe", new HashSet<>());
        assertNotEquals(recipeSearchDto, new RecipeSearchDto(3, 1, "JaneDoe", new HashSet<>()));
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals6() {
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto(3, 1, "Author", new HashSet<>());
        assertNotEquals(recipeSearchDto, new RecipeSearchDto(3, 1, "JaneDoe", new HashSet<>()));
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals7() {
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto(3, 1, null, new HashSet<>());
        assertNotEquals(recipeSearchDto, new RecipeSearchDto(3, 1, "JaneDoe", new HashSet<>()));
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals8() {
        HashSet<String> stringSet = new HashSet<>();
        stringSet.add("JaneDoe");
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto(3, 1, "JaneDoe", stringSet);
        assertNotEquals(recipeSearchDto, new RecipeSearchDto(3, 1, "JaneDoe", new HashSet<>()));
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals9() {
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto(3, 1, null, new HashSet<>());
        RecipeSearchDto recipeSearchDto1 = new RecipeSearchDto(3, 1, null, new HashSet<>());

        assertEquals(recipeSearchDto, recipeSearchDto1);
        int expectedHashCodeResult = recipeSearchDto.hashCode();
        assertEquals(expectedHashCodeResult, recipeSearchDto1.hashCode());
    }
}

