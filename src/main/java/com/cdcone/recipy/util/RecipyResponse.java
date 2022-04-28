package com.cdcone.recipy.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RecipyResponse {
    private final Object data;
    private final String message;

    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private final LocalDateTime timestamp = LocalDateTime.now();

    public RecipyResponse(Object data, String message) {
        this.data = data;
        this.message = message;
    }
}
