package com.example.transaction.exception;

import com.example.transaction.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResponseEntityExceptonHandler
{
    @ExceptionHandler(CategoryNotFound.class)
    public ResponseEntity<ErrorResponse> handlecategoryException(CategoryNotFound categoryNotFound){
        return new ResponseEntity<>(new ErrorResponse()
                .builder()
                .errorCode(categoryNotFound.getErrorMessage())
                .errorMessage(categoryNotFound.getMessage())
                .build(), HttpStatus.BAD_REQUEST
        );
    }



}
