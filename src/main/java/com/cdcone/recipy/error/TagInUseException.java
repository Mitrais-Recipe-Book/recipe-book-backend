package com.cdcone.recipy.error;

public class TagInUseException extends RuntimeException {
    public TagInUseException() {
        super("failed: tag is used by recipe");
    }
}
