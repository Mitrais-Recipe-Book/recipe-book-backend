package com.cdcone.recipy.error.handler;

import com.cdcone.recipy.dto.response.CommonResponse;
import com.cdcone.recipy.error.PasswordNotMatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PasswordNotMatchException.class)
    protected ResponseEntity<CommonResponse> handlePasswordNotMatch(
            PasswordNotMatchException e) {
        CommonResponse body = new CommonResponse(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<CommonResponse> handleEntityNotFound(
            EntityNotFoundException e) {
        CommonResponse body = new CommonResponse("failed: " + e.getMessage() + " not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                new CommonResponse("failed: invalid Request Body", null),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    protected ResponseEntity<CommonResponse> handleMaxUploadSizeExceeded(
            MaxUploadSizeExceededException e) {
        String msg = "failed: uploaded file is too large.";
        return ResponseEntity.badRequest().body(new CommonResponse(msg));
    }
}
