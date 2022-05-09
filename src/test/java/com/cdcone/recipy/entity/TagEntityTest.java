package com.cdcone.recipy.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TagEntityTest {
    /**
     * Method under test: {@link TagEntity#TagEntity(String)}
     */
    @Test
    void testConstructor() {
        TagEntity actualTagEntity = new TagEntity("Name");
        assertEquals(0, actualTagEntity.getId());
        assertEquals("Name", actualTagEntity.getName());
    }
}

