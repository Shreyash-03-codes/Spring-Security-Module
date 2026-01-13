package com.springboot.testing.dto.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ApiError {
    private final String status;
    private final String description;
}
