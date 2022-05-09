package com.cdcone.recipy.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class RecipeSearchDtoTest {
    /**
     * Method under test: {@link RecipeSearchDto#canEqual(Object)}
     */
    @Test
    void testCanEqual() {
        assertFalse((new RecipeSearchDto("Search", "JaneDoe", new HashSet<>(), 1)).canEqual("Other"));
    }

    /**
     * Method under test: {@link RecipeSearchDto#canEqual(Object)}
     */
    @Test
    void testCanEqual2() {
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto("Search", "JaneDoe", new HashSet<>(), 1);
        assertTrue(recipeSearchDto.canEqual(new RecipeSearchDto("Search", "JaneDoe", new HashSet<>(), 3)));
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link RecipeSearchDto#RecipeSearchDto(String, String, Set, int)}
     *   <li>{@link RecipeSearchDto#setAuthor(String)}
     *   <li>{@link RecipeSearchDto#setPage(int)}
     *   <li>{@link RecipeSearchDto#setSearch(String)}
     *   <li>{@link RecipeSearchDto#setTags(Set)}
     * </ul>
     */
    @Test
    void testConstructor() {
        RecipeSearchDto actualRecipeSearchDto = new RecipeSearchDto("Search", "JaneDoe", new HashSet<>(), 1);
        actualRecipeSearchDto.setAuthor("JaneDoe");
        actualRecipeSearchDto.setPage(1);
        actualRecipeSearchDto.setSearch("Search");
        HashSet<String> stringSet = new HashSet<>();
        actualRecipeSearchDto.setTags(stringSet);
        assertEquals("JaneDoe", actualRecipeSearchDto.getAuthor());
        assertEquals(1, actualRecipeSearchDto.getPage());
        assertEquals("Search", actualRecipeSearchDto.getSearch());
        assertSame(stringSet, actualRecipeSearchDto.getTags());
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals() {
        assertNotEquals(new RecipeSearchDto("Search", "JaneDoe", new HashSet<>(), 1), null);
        assertNotEquals(new RecipeSearchDto("Search", "JaneDoe", new HashSet<>(), 1), "Different type to RecipeSearchDto");
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals2() {
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto("Search", "JaneDoe", new HashSet<>(), 1);
        assertEquals(recipeSearchDto, recipeSearchDto);
        int expectedHashCodeResult = recipeSearchDto.hashCode();
        assertEquals(expectedHashCodeResult, recipeSearchDto.hashCode());
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals3() {
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto("Search", "JaneDoe", new HashSet<>(), 1);
        RecipeSearchDto recipeSearchDto1 = new RecipeSearchDto("Search", "JaneDoe", new HashSet<>(), 1);

        assertEquals(recipeSearchDto, recipeSearchDto1);
        int expectedHashCodeResult = recipeSearchDto.hashCode();
        assertEquals(expectedHashCodeResult, recipeSearchDto1.hashCode());
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals4() {
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto("JaneDoe", "JaneDoe", new HashSet<>(), 1);
        assertNotEquals(recipeSearchDto, new RecipeSearchDto("Search", "JaneDoe", new HashSet<>(), 1));
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals5() {
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto(null, "JaneDoe", new HashSet<>(), 1);
        assertNotEquals(recipeSearchDto, new RecipeSearchDto("Search", "JaneDoe", new HashSet<>(), 1));
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals6() {
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto("Search", "Search", new HashSet<>(), 1);
        assertNotEquals(recipeSearchDto, new RecipeSearchDto("Search", "JaneDoe", new HashSet<>(), 1));
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals7() {
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto("Search", null, new HashSet<>(), 1);
        assertNotEquals(recipeSearchDto, new RecipeSearchDto("Search", "JaneDoe", new HashSet<>(), 1));
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals8() {
        HashSet<String> stringSet = new HashSet<>();
        stringSet.add("Search");
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto("Search", "JaneDoe", stringSet, 1);
        assertNotEquals(recipeSearchDto, new RecipeSearchDto("Search", "JaneDoe", new HashSet<>(), 1));
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals9() {
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto("Search", "JaneDoe", new HashSet<>(), 3);
        assertNotEquals(recipeSearchDto, new RecipeSearchDto("Search", "JaneDoe", new HashSet<>(), 1));
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals10() {
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto(null, "JaneDoe", new HashSet<>(), 1);
        RecipeSearchDto recipeSearchDto1 = new RecipeSearchDto(null, "JaneDoe", new HashSet<>(), 1);

        assertEquals(recipeSearchDto, recipeSearchDto1);
        int expectedHashCodeResult = recipeSearchDto.hashCode();
        assertEquals(expectedHashCodeResult, recipeSearchDto1.hashCode());
    }

    /**
     * Method under test: {@link RecipeSearchDto#equals(Object)}
     */
    @Test
    void testEquals11() {
        RecipeSearchDto recipeSearchDto = new RecipeSearchDto("Search", null, new HashSet<>(), 1);
        RecipeSearchDto recipeSearchDto1 = new RecipeSearchDto("Search", null, new HashSet<>(), 1);

        assertEquals(recipeSearchDto, recipeSearchDto1);
        int expectedHashCodeResult = recipeSearchDto.hashCode();
        assertEquals(expectedHashCodeResult, recipeSearchDto1.hashCode());
    }
}

