package com.cdcone.recipy.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class ErrorClauseTest {
    /**
     * Method under test: {@link ErrorClause#Check(HttpStatus, Exception)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCheck() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Throwable.toString()" because the return value of "java.lang.Exception.getCause()" is null
        //       at com.cdcone.recipy.response.ErrorClause.Check(ErrorClause.java:7)
        //   In order to prevent Check(HttpStatus, Exception)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   Check(HttpStatus, Exception).
        //   See https://diff.blue/R013 to resolve this issue.

        ErrorClause.Check(HttpStatus.CONTINUE, new Exception("An error occurred"));
    }

    /**
     * Method under test: {@link ErrorClause#Check(HttpStatus, Exception)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCheck2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Exception.getCause()" because "e" is null
        //       at com.cdcone.recipy.response.ErrorClause.Check(ErrorClause.java:7)
        //   In order to prevent Check(HttpStatus, Exception)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   Check(HttpStatus, Exception).
        //   See https://diff.blue/R013 to resolve this issue.

        ErrorClause.Check(HttpStatus.CONTINUE, null);
    }

    /**
     * Method under test: {@link ErrorClause#Check(HttpStatus, Exception)}
     */
    @Test
    void testCheck3() {
        CommonResponse actualCheckResult = ErrorClause.Check(HttpStatus.CONTINUE, new IOException(new Throwable()));
        assertEquals("java.lang.Throwable", actualCheckResult.getMessage());
        assertEquals(HttpStatus.NOT_IMPLEMENTED, actualCheckResult.getStatus());
        assertNull(actualCheckResult.getPayload());
    }

    /**
     * Method under test: {@link ErrorClause#Check(HttpStatus, Exception)}
     */
    @Test
    void testCheck4() {
        CommonResponse actualCheckResult = ErrorClause.Check(HttpStatus.SWITCHING_PROTOCOLS,
                new IOException(new Throwable()));
        assertEquals("java.lang.Throwable", actualCheckResult.getMessage());
        assertEquals(HttpStatus.NOT_IMPLEMENTED, actualCheckResult.getStatus());
        assertNull(actualCheckResult.getPayload());
    }

    /**
     * Method under test: {@link ErrorClause#Check(HttpStatus, Exception)}
     */
    @Test
    void testCheck5() {
        CommonResponse actualCheckResult = ErrorClause.Check(HttpStatus.PROCESSING, new IOException(new Throwable()));
        assertEquals("java.lang.Throwable", actualCheckResult.getMessage());
        assertEquals(HttpStatus.NOT_IMPLEMENTED, actualCheckResult.getStatus());
        assertNull(actualCheckResult.getPayload());
    }

    /**
     * Method under test: {@link ErrorClause#Check(HttpStatus, Exception)}
     */
    @Test
    void testCheck6() {
        CommonResponse actualCheckResult = ErrorClause.Check(HttpStatus.CHECKPOINT, new IOException(new Throwable()));
        assertEquals("java.lang.Throwable", actualCheckResult.getMessage());
        assertEquals(HttpStatus.NOT_IMPLEMENTED, actualCheckResult.getStatus());
        assertNull(actualCheckResult.getPayload());
    }

    /**
     * Method under test: {@link ErrorClause#Check(HttpStatus, Exception)}
     */
    @Test
    void testCheck7() {
        CommonResponse actualCheckResult = ErrorClause.Check(HttpStatus.CONTINUE,
                new IOException(new IllegalStateException("ConstraintViolationException")));
        assertEquals("Data must be unique", actualCheckResult.getMessage());
        assertEquals(HttpStatus.CONTINUE, actualCheckResult.getStatus());
        assertNull(actualCheckResult.getPayload());
    }

    /**
     * Method under test: {@link ErrorClause#Check(HttpStatus, Exception)}
     */
    @Test
    void testCheck8() {
        CommonResponse actualCheckResult = ErrorClause.Check(HttpStatus.OK, new IOException(new Throwable()));
        assertEquals("java.lang.Throwable", actualCheckResult.getMessage());
        assertEquals(HttpStatus.NOT_IMPLEMENTED, actualCheckResult.getStatus());
        assertNull(actualCheckResult.getPayload());
    }

    /**
     * Method under test: {@link ErrorClause#Check(HttpStatus, Exception)}
     */
    @Test
    void testCheck9() {
        CommonResponse actualCheckResult = ErrorClause.Check(HttpStatus.CREATED, new IOException(new Throwable()));
        assertEquals("java.lang.Throwable", actualCheckResult.getMessage());
        assertEquals(HttpStatus.NOT_IMPLEMENTED, actualCheckResult.getStatus());
        assertNull(actualCheckResult.getPayload());
    }

    /**
     * Method under test: {@link ErrorClause#Check(HttpStatus, Exception)}
     */
    @Test
    void testCheck10() {
        CommonResponse actualCheckResult = ErrorClause.Check(HttpStatus.ACCEPTED, new IOException(new Throwable()));
        assertEquals("java.lang.Throwable", actualCheckResult.getMessage());
        assertEquals(HttpStatus.NOT_IMPLEMENTED, actualCheckResult.getStatus());
        assertNull(actualCheckResult.getPayload());
    }
}

