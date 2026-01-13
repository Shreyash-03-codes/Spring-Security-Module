package com.security.learn.dto.signup;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SignupRequestDto {

    private String username;

    private String name;

    private String password;
}
