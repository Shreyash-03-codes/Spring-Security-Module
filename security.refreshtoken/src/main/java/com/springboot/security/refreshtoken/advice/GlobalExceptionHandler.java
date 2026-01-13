package com.springboot.security.refreshtoken.advice;

import com.springboot.security.refreshtoken.dto.datatranmission.ApiResponse;
import com.springboot.security.refreshtoken.dto.error.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<ApiError>> runTimeExceptionHandler(RuntimeException exception){
        ApiError apiError=new ApiError(HttpStatus.BAD_REQUEST.name(), exception.getLocalizedMessage());
        ApiResponse<ApiError> apiResponse=new ApiResponse<>(apiError);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiResponse<ApiError>> illegalStateExceptionHandler(IllegalStateException exception){
        ApiError apiError=new ApiError(HttpStatus.UNAUTHORIZED.name(),  exception.getLocalizedMessage());
        ApiResponse<ApiError> apiResponse=new ApiResponse<>(apiError);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ApiError>> exceptionHandler(Exception exception){
        ApiError apiError=new ApiError(HttpStatus.BAD_REQUEST.name(),  exception.getLocalizedMessage());
        ApiResponse<ApiError> apiResponse=new ApiResponse<>(apiError);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

}
