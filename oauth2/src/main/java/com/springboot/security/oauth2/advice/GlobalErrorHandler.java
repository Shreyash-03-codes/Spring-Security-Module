package com.springboot.security.oauth2.advice;

import com.springboot.security.oauth2.dto.error.ApiError;
import com.springboot.security.oauth2.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ApiError>> exception(Exception e){
        ApiError apiError=new ApiError(HttpStatus.BAD_REQUEST.name(),e.getLocalizedMessage());
        ApiResponse<ApiError> apiResponse=new ApiResponse<>(apiError);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
