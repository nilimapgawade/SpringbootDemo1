package com.collections.genesys.Exception;


import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.collections.genesys.advices.ApiErrorResponse;
import com.collections.genesys.advices.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, ErrorDetails errorDetails ) {

        ApiErrorResponse failureResponse = new ApiErrorResponse(
                        Collections.singletonList(errorDetails));
        return new ResponseEntity<>(failureResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IlligalArgumentWithObjectException.class)
    public ResponseEntity<Object> handleIlligalArgumentWithObjectException(IlligalArgumentWithObjectException ex ) {
        ErrorDetails errorDetails = (ErrorDetails) ex.getErrorDetails();
        ApiErrorResponse failureResponse = new ApiErrorResponse(
                Collections.singletonList(errorDetails));
        return new ResponseEntity<>(failureResponse, HttpStatus.BAD_REQUEST);
    }
}
