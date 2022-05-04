package com.cdcone.recipy.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SignInDtoTest {
    /**
     * Method under test: {@link SignInDto#canEqual(Object)}
     */
    @Test
    void testCanEqual() {
        assertFalse((new SignInDto()).canEqual("Other"));
    }

    /**
     * Method under test: {@link SignInDto#canEqual(Object)}
     */
    @Test
    void testCanEqual2() {
        SignInDto signInDto = new SignInDto();

        SignInDto signInDto1 = new SignInDto();
        signInDto1.setPassword("iloveyou");
        signInDto1.setUsername("janedoe");
        assertTrue(signInDto.canEqual(signInDto1));
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>default or parameterless constructor of {@link SignInDto}
     *   <li>{@link SignInDto#setPassword(String)}
     *   <li>{@link SignInDto#setUsername(String)}
     * </ul>
     */
    @Test
    void testConstructor() {
        SignInDto actualSignInDto = new SignInDto();
        actualSignInDto.setPassword("iloveyou");
        actualSignInDto.setUsername("janedoe");
        assertEquals("iloveyou", actualSignInDto.getPassword());
        assertEquals("janedoe", actualSignInDto.getUsername());
    }

    /**
     * Method under test: {@link SignInDto#equals(Object)}
     */
    @Test
    void testEquals() {
        SignInDto signInDto = new SignInDto();
        signInDto.setPassword("iloveyou");
        signInDto.setUsername("janedoe");
        assertNotEquals(signInDto, null);
    }

    /**
     * Method under test: {@link SignInDto#equals(Object)}
     */
    @Test
    void testEquals2() {
        SignInDto signInDto = new SignInDto();
        signInDto.setPassword("iloveyou");
        signInDto.setUsername("janedoe");
        assertNotEquals(signInDto, "Different type to SignInDto");
    }

    /**
     * Method under test: {@link SignInDto#equals(Object)}
     */
    @Test
    void testEquals3() {
        SignInDto signInDto = new SignInDto();
        signInDto.setPassword("iloveyou");
        signInDto.setUsername("janedoe");
        assertEquals(signInDto, signInDto);
        int expectedHashCodeResult = signInDto.hashCode();
        assertEquals(expectedHashCodeResult, signInDto.hashCode());
    }

    /**
     * Method under test: {@link SignInDto#equals(Object)}
     */
    @Test
    void testEquals4() {
        SignInDto signInDto = new SignInDto();
        signInDto.setPassword("iloveyou");
        signInDto.setUsername("janedoe");

        SignInDto signInDto1 = new SignInDto();
        signInDto1.setPassword("iloveyou");
        signInDto1.setUsername("janedoe");
        assertEquals(signInDto, signInDto1);
        int expectedHashCodeResult = signInDto.hashCode();
        assertEquals(expectedHashCodeResult, signInDto1.hashCode());
    }

    /**
     * Method under test: {@link SignInDto#equals(Object)}
     */
    @Test
    void testEquals5() {
        SignInDto signInDto = new SignInDto();
        signInDto.setPassword("janedoe");
        signInDto.setUsername("janedoe");

        SignInDto signInDto1 = new SignInDto();
        signInDto1.setPassword("iloveyou");
        signInDto1.setUsername("janedoe");
        assertNotEquals(signInDto, signInDto1);
    }

    /**
     * Method under test: {@link SignInDto#equals(Object)}
     */
    @Test
    void testEquals6() {
        SignInDto signInDto = new SignInDto();
        signInDto.setPassword(null);
        signInDto.setUsername("janedoe");

        SignInDto signInDto1 = new SignInDto();
        signInDto1.setPassword("iloveyou");
        signInDto1.setUsername("janedoe");
        assertNotEquals(signInDto, signInDto1);
    }

    /**
     * Method under test: {@link SignInDto#equals(Object)}
     */
    @Test
    void testEquals7() {
        SignInDto signInDto = new SignInDto();
        signInDto.setPassword("iloveyou");
        signInDto.setUsername("iloveyou");

        SignInDto signInDto1 = new SignInDto();
        signInDto1.setPassword("iloveyou");
        signInDto1.setUsername("janedoe");
        assertNotEquals(signInDto, signInDto1);
    }

    /**
     * Method under test: {@link SignInDto#equals(Object)}
     */
    @Test
    void testEquals8() {
        SignInDto signInDto = new SignInDto();
        signInDto.setPassword("iloveyou");
        signInDto.setUsername(null);

        SignInDto signInDto1 = new SignInDto();
        signInDto1.setPassword("iloveyou");
        signInDto1.setUsername("janedoe");
        assertNotEquals(signInDto, signInDto1);
    }

    /**
     * Method under test: {@link SignInDto#equals(Object)}
     */
    @Test
    void testEquals9() {
        SignInDto signInDto = new SignInDto();
        signInDto.setPassword(null);
        signInDto.setUsername("janedoe");

        SignInDto signInDto1 = new SignInDto();
        signInDto1.setPassword(null);
        signInDto1.setUsername("janedoe");
        assertEquals(signInDto, signInDto1);
        int expectedHashCodeResult = signInDto.hashCode();
        assertEquals(expectedHashCodeResult, signInDto1.hashCode());
    }

    /**
     * Method under test: {@link SignInDto#equals(Object)}
     */
    @Test
    void testEquals10() {
        SignInDto signInDto = new SignInDto();
        signInDto.setPassword("iloveyou");
        signInDto.setUsername(null);

        SignInDto signInDto1 = new SignInDto();
        signInDto1.setPassword("iloveyou");
        signInDto1.setUsername(null);
        assertEquals(signInDto, signInDto1);
        int expectedHashCodeResult = signInDto.hashCode();
        assertEquals(expectedHashCodeResult, signInDto1.hashCode());
    }
}

