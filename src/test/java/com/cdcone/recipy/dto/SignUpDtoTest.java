package com.cdcone.recipy.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SignUpDtoTest {
    /**
     * Method under test: {@link SignUpDto#canEqual(Object)}
     */
    @Test
    void testCanEqual() {
        assertFalse((new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe")).canEqual("Other"));
    }

    /**
     * Method under test: {@link SignUpDto#canEqual(Object)}
     */
    @Test
    void testCanEqual2() {
        SignUpDto signUpDto = new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe");
        assertTrue(signUpDto.canEqual(new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe")));
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link SignUpDto#SignUpDto(String, String, String, String)}
     *   <li>{@link SignUpDto#setEmail(String)}
     *   <li>{@link SignUpDto#setFullName(String)}
     *   <li>{@link SignUpDto#setPassword(String)}
     *   <li>{@link SignUpDto#setUsername(String)}
     * </ul>
     */
    @Test
    void testConstructor() {
        SignUpDto actualSignUpDto = new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe");
        actualSignUpDto.setEmail("jane.doe@example.org");
        actualSignUpDto.setFullName("Dr Jane Doe");
        actualSignUpDto.setPassword("iloveyou");
        actualSignUpDto.setUsername("janedoe");
        assertEquals("jane.doe@example.org", actualSignUpDto.getEmail());
        assertEquals("Dr Jane Doe", actualSignUpDto.getFullName());
        assertEquals("iloveyou", actualSignUpDto.getPassword());
        assertEquals("janedoe", actualSignUpDto.getUsername());
    }

    /**
     * Method under test: {@link SignUpDto#equals(Object)}
     */
    @Test
    void testEquals() {
        assertNotEquals(new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe"), null);
        assertNotEquals(new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe"),
                "Different type to SignUpDto");
    }

    /**
     * Method under test: {@link SignUpDto#equals(Object)}
     */
    @Test
    void testEquals2() {
        SignUpDto signUpDto = new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe");
        assertEquals(signUpDto, signUpDto);
        int expectedHashCodeResult = signUpDto.hashCode();
        assertEquals(expectedHashCodeResult, signUpDto.hashCode());
    }

    /**
     * Method under test: {@link SignUpDto#equals(Object)}
     */
    @Test
    void testEquals3() {
        SignUpDto signUpDto = new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe");
        SignUpDto signUpDto1 = new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe");

        assertEquals(signUpDto, signUpDto1);
        int expectedHashCodeResult = signUpDto.hashCode();
        assertEquals(expectedHashCodeResult, signUpDto1.hashCode());
    }

    /**
     * Method under test: {@link SignUpDto#equals(Object)}
     */
    @Test
    void testEquals4() {
        SignUpDto signUpDto = new SignUpDto("janedoe", "janedoe", "iloveyou", "Dr Jane Doe");
        assertNotEquals(signUpDto, new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe"));
    }

    /**
     * Method under test: {@link SignUpDto#equals(Object)}
     */
    @Test
    void testEquals5() {
        SignUpDto signUpDto = new SignUpDto(null, "janedoe", "iloveyou", "Dr Jane Doe");
        assertNotEquals(signUpDto, new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe"));
    }

    /**
     * Method under test: {@link SignUpDto#equals(Object)}
     */
    @Test
    void testEquals6() {
        SignUpDto signUpDto = new SignUpDto("jane.doe@example.org", "jane.doe@example.org", "iloveyou", "Dr Jane Doe");
        assertNotEquals(signUpDto, new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe"));
    }

    /**
     * Method under test: {@link SignUpDto#equals(Object)}
     */
    @Test
    void testEquals7() {
        SignUpDto signUpDto = new SignUpDto("jane.doe@example.org", null, "iloveyou", "Dr Jane Doe");
        assertNotEquals(signUpDto, new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe"));
    }

    /**
     * Method under test: {@link SignUpDto#equals(Object)}
     */
    @Test
    void testEquals8() {
        SignUpDto signUpDto = new SignUpDto("jane.doe@example.org", "janedoe", "jane.doe@example.org", "Dr Jane Doe");
        assertNotEquals(signUpDto, new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe"));
    }

    /**
     * Method under test: {@link SignUpDto#equals(Object)}
     */
    @Test
    void testEquals9() {
        SignUpDto signUpDto = new SignUpDto("jane.doe@example.org", "janedoe", null, "Dr Jane Doe");
        assertNotEquals(signUpDto, new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe"));
    }

    /**
     * Method under test: {@link SignUpDto#equals(Object)}
     */
    @Test
    void testEquals10() {
        SignUpDto signUpDto = new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "jane.doe@example.org");
        assertNotEquals(signUpDto, new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe"));
    }

    /**
     * Method under test: {@link SignUpDto#equals(Object)}
     */
    @Test
    void testEquals11() {
        SignUpDto signUpDto = new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", null);
        assertNotEquals(signUpDto, new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", "Dr Jane Doe"));
    }

    /**
     * Method under test: {@link SignUpDto#equals(Object)}
     */
    @Test
    void testEquals12() {
        SignUpDto signUpDto = new SignUpDto(null, "janedoe", "iloveyou", "Dr Jane Doe");
        SignUpDto signUpDto1 = new SignUpDto(null, "janedoe", "iloveyou", "Dr Jane Doe");

        assertEquals(signUpDto, signUpDto1);
        int expectedHashCodeResult = signUpDto.hashCode();
        assertEquals(expectedHashCodeResult, signUpDto1.hashCode());
    }

    /**
     * Method under test: {@link SignUpDto#equals(Object)}
     */
    @Test
    void testEquals13() {
        SignUpDto signUpDto = new SignUpDto("jane.doe@example.org", null, "iloveyou", "Dr Jane Doe");
        SignUpDto signUpDto1 = new SignUpDto("jane.doe@example.org", null, "iloveyou", "Dr Jane Doe");

        assertEquals(signUpDto, signUpDto1);
        int expectedHashCodeResult = signUpDto.hashCode();
        assertEquals(expectedHashCodeResult, signUpDto1.hashCode());
    }

    /**
     * Method under test: {@link SignUpDto#equals(Object)}
     */
    @Test
    void testEquals14() {
        SignUpDto signUpDto = new SignUpDto("jane.doe@example.org", "janedoe", null, "Dr Jane Doe");
        SignUpDto signUpDto1 = new SignUpDto("jane.doe@example.org", "janedoe", null, "Dr Jane Doe");

        assertEquals(signUpDto, signUpDto1);
        int expectedHashCodeResult = signUpDto.hashCode();
        assertEquals(expectedHashCodeResult, signUpDto1.hashCode());
    }

    /**
     * Method under test: {@link SignUpDto#equals(Object)}
     */
    @Test
    void testEquals15() {
        SignUpDto signUpDto = new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", null);
        SignUpDto signUpDto1 = new SignUpDto("jane.doe@example.org", "janedoe", "iloveyou", null);

        assertEquals(signUpDto, signUpDto1);
        int expectedHashCodeResult = signUpDto.hashCode();
        assertEquals(expectedHashCodeResult, signUpDto1.hashCode());
    }
}

