package com.cdcone.recipy.response;

import org.springframework.http.HttpStatus;

public class ErrorClause {
    public static CommonResponse Check(HttpStatus status, Exception e){
        String message = e.getCause().toString();

        if (message.contains("ConstraintViolationException")){
            return new CommonResponse(status, "Data must be unique");
        }

        if (message.contains("NullPointerException")){            
            return new CommonResponse(status, "Data is null");
        }

        return new CommonResponse(HttpStatus.NOT_IMPLEMENTED, message);
    }    
}
