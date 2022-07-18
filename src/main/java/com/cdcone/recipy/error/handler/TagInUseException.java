package com.cdcone.recipy.error.handler;

public class TagInUseException extends RuntimeException {
    public TagInUseException() {
        super("Failed: tag is in use.");
    }
}
