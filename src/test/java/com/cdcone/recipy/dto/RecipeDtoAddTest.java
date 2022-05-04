package com.cdcone.recipy.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RecipeDtoAddTest {
    /**
     * Method under test: {@link RecipeDtoAdd#canEqual(Object)}
     */
    @Test
    void testCanEqual() {
        assertFalse((new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'})).canEqual("Other"));
    }

    /**
     * Method under test: {@link RecipeDtoAdd#canEqual(Object)}
     */
    @Test
    void testCanEqual2() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'});
        assertTrue(recipeDtoAdd.canEqual(new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients",
                "Not all who wander are lost", "https://example.org/example", true, new Byte[]{'A'})));
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link RecipeDtoAdd#RecipeDtoAdd(Long, String, String, String, String, String, boolean, Byte[])}
     *   <li>{@link RecipeDtoAdd#setBannerImage(Byte[])}
     *   <li>{@link RecipeDtoAdd#setContent(String)}
     *   <li>{@link RecipeDtoAdd#setDraft(boolean)}
     *   <li>{@link RecipeDtoAdd#setIngredients(String)}
     *   <li>{@link RecipeDtoAdd#setOverview(String)}
     *   <li>{@link RecipeDtoAdd#setTitle(String)}
     *   <li>{@link RecipeDtoAdd#setUserId(Long)}
     *   <li>{@link RecipeDtoAdd#setVideoURL(String)}
     * </ul>
     */
    @Test
    void testConstructor() {
        RecipeDtoAdd actualRecipeDtoAdd = new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients",
                "Not all who wander are lost", "https://example.org/example", true, new Byte[]{'A'});
        actualRecipeDtoAdd.setBannerImage(new Byte[]{'A'});
        actualRecipeDtoAdd.setContent("Not all who wander are lost");
        actualRecipeDtoAdd.setDraft(true);
        actualRecipeDtoAdd.setIngredients("Ingredients");
        actualRecipeDtoAdd.setOverview("Overview");
        actualRecipeDtoAdd.setTitle("Dr");
        actualRecipeDtoAdd.setUserId(123L);
        actualRecipeDtoAdd.setVideoURL("https://example.org/example");
        assertEquals("Not all who wander are lost", actualRecipeDtoAdd.getContent());
        assertEquals("Ingredients", actualRecipeDtoAdd.getIngredients());
        assertEquals("Overview", actualRecipeDtoAdd.getOverview());
        assertEquals("Dr", actualRecipeDtoAdd.getTitle());
        assertEquals(123L, actualRecipeDtoAdd.getUserId().longValue());
        assertEquals("https://example.org/example", actualRecipeDtoAdd.getVideoURL());
        assertTrue(actualRecipeDtoAdd.isDraft());
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals() {
        assertNotEquals(new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'}), null);
        assertNotEquals(new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'}), "Different type to RecipeDtoAdd");
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals2() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'});
        assertEquals(recipeDtoAdd, recipeDtoAdd);
        int expectedHashCodeResult = recipeDtoAdd.hashCode();
        assertEquals(expectedHashCodeResult, recipeDtoAdd.hashCode());
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals3() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'});
        RecipeDtoAdd recipeDtoAdd1 = new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'});

        assertEquals(recipeDtoAdd, recipeDtoAdd1);
        int expectedHashCodeResult = recipeDtoAdd.hashCode();
        assertEquals(expectedHashCodeResult, recipeDtoAdd1.hashCode());
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals4() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(1L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'});
        assertNotEquals(recipeDtoAdd, new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'}));
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals5() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(null, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'});
        assertNotEquals(recipeDtoAdd, new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'}));
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals6() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(123L, "Overview", "Overview", "Ingredients",
                "Not all who wander are lost", "https://example.org/example", true, new Byte[]{'A'});
        assertNotEquals(recipeDtoAdd, new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'}));
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals7() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(123L, null, "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'});
        assertNotEquals(recipeDtoAdd, new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'}));
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals8() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(123L, "Dr", "Dr", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'});
        assertNotEquals(recipeDtoAdd, new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'}));
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals9() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(123L, "Dr", null, "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'});
        assertNotEquals(recipeDtoAdd, new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'}));
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals10() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(123L, "Dr", "Overview", "Dr", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'});
        assertNotEquals(recipeDtoAdd, new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'}));
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals11() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(123L, "Dr", "Overview", null, "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'});
        assertNotEquals(recipeDtoAdd, new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'}));
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals12() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Dr",
                "https://example.org/example", true, new Byte[]{'A'});
        assertNotEquals(recipeDtoAdd, new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'}));
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals13() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", null,
                "https://example.org/example", true, new Byte[]{'A'});
        assertNotEquals(recipeDtoAdd, new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'}));
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals14() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "Dr", true, new Byte[]{'A'});
        assertNotEquals(recipeDtoAdd, new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'}));
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals15() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                null, true, new Byte[]{'A'});
        assertNotEquals(recipeDtoAdd, new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'}));
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals16() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", false, new Byte[]{'A'});
        assertNotEquals(recipeDtoAdd, new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'}));
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals17() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{1});
        assertNotEquals(recipeDtoAdd, new RecipeDtoAdd(123L, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'}));
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals18() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(null, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'});
        RecipeDtoAdd recipeDtoAdd1 = new RecipeDtoAdd(null, "Dr", "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'});

        assertEquals(recipeDtoAdd, recipeDtoAdd1);
        int expectedHashCodeResult = recipeDtoAdd.hashCode();
        assertEquals(expectedHashCodeResult, recipeDtoAdd1.hashCode());
    }

    /**
     * Method under test: {@link RecipeDtoAdd#equals(Object)}
     */
    @Test
    void testEquals19() {
        RecipeDtoAdd recipeDtoAdd = new RecipeDtoAdd(123L, null, "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'});
        RecipeDtoAdd recipeDtoAdd1 = new RecipeDtoAdd(123L, null, "Overview", "Ingredients", "Not all who wander are lost",
                "https://example.org/example", true, new Byte[]{'A'});

        assertEquals(recipeDtoAdd, recipeDtoAdd1);
        int expectedHashCodeResult = recipeDtoAdd.hashCode();
        assertEquals(expectedHashCodeResult, recipeDtoAdd1.hashCode());
    }
}

