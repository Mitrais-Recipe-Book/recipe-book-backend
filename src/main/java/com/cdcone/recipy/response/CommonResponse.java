package com.cdcone.recipy.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonResponse {
    private HttpStatus status;
    private String message = "SUCCESS";
    private Object payload = null;


    public CommonResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
    
    public CommonResponse(HttpStatus status,Object payload) {
        this.status = status;
        this.payload = payload;
    }
}
