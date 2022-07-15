package com.cdcone.recipy.error;

public class PasswordNotMatchException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Password does not match.";
    }
}
