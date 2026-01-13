package com.springboot.security.oauth2.dto.response;


import com.springboot.security.oauth2.dto.error.ApiError;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse<T> {

    private LocalDateTime dateTime;
    private T data;
    private ApiError error;

    public ApiResponse(){
        dateTime=LocalDateTime.now();
    }

    public ApiResponse(T data){
        this();
        this.data=data;
    }

    public ApiResponse(ApiError error){
        this();
        this.error=error;
    }
}
