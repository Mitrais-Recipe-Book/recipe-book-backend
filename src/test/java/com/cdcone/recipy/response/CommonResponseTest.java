package com.cdcone.recipy.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class CommonResponseTest {
    /**
     * Method under test: {@link CommonResponse#CommonResponse(HttpStatus, Object)}
     */
    @Test
    void testConstructor() {
        CommonResponse actualCommonResponse = new CommonResponse(HttpStatus.CONTINUE, (Object) "Payload");

        assertEquals("SUCCESS", actualCommonResponse.getMessage());
        assertEquals(HttpStatus.CONTINUE, actualCommonResponse.getStatus());
        assertEquals("Payload", actualCommonResponse.getPayload());
    }

    /**
     * Method under test: {@link CommonResponse#CommonResponse(HttpStatus, String)}
     */
    @Test
    void testConstructor2() {
        CommonResponse actualCommonResponse = new CommonResponse(HttpStatus.CONTINUE, "Not all who wander are lost");

        assertEquals("Not all who wander are lost", actualCommonResponse.getMessage());
        assertEquals(HttpStatus.CONTINUE, actualCommonResponse.getStatus());
        assertNull(actualCommonResponse.getPayload());
    }
}

