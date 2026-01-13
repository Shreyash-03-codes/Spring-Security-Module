package com.springboot.security.session.dto.response;

import com.springboot.security.session.dto.error.ApiError;
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

    public ApiResponse(ApiError apiError){
        this();
        this.error=apiError;
    }


}
