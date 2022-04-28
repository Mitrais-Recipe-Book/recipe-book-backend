package com.cdcone.recipy.response;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasicResponse {
    private HttpStatus status;
    private String message;
}
