package com.cdcone.recipy.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonResponse {
    private String message = "SUCCESS";
    private Object payload = null;


    public CommonResponse(String message) {
        this.message = message;
    }

    public CommonResponse(Object payload){
        this.payload = payload;
    }
}
