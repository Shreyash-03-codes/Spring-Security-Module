package com.springboot.security.session.dto.error;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private String status;
    private String description;
}
