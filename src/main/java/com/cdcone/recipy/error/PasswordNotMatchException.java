package com.cdcone.recipy.error;

public class PasswordNotMatchException extends RuntimeException {
    @Override
    public String getMessage() {
        return "failed: password does not match.";
    }
}
