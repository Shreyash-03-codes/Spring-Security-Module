package com.springboot.testing.advice;

import com.springboot.testing.dto.error.ApiError;
import com.springboot.testing.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiResponse<ApiError>> exception(Exception e){
        ApiError apiError=new ApiError(HttpStatus.BAD_REQUEST.name(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(apiError));
    }
}
