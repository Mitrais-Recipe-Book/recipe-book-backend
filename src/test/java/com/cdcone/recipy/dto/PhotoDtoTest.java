package com.cdcone.recipy.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

class PhotoDtoTest {
    /**
     * Method under test: default or parameterless constructor of {@link PhotoDto}
     */
    @Test
    void testConstructor() throws UnsupportedEncodingException {
        assertEquals("Type", (new PhotoDto("Type", "AAAAAAAA".getBytes("UTF-8"))).getType());
    }
}

