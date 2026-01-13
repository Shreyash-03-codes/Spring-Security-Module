package com.springboot.security.refreshtoken.dto.datatranmission;

import com.springboot.security.refreshtoken.dto.error.ApiError;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse<T> {
    private LocalDateTime dateTime;
    private T data;
    private ApiError error;

    ApiResponse(){
        dateTime=LocalDateTime.now();
    }

    public ApiResponse(T data){
        this();
        this.data=data;
    }

    public ApiResponse(ApiError apiError){
        this();
        this.error=apiError;
    }
}
