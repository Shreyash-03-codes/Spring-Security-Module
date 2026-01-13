package com.security.learn.dto.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class ApiError {

    private String status;
    private String message;
}
