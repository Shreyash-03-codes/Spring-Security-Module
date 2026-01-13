package com.springboot.security.session.dto.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequestDto {

    private String name;

    private String username;

    private String password;
}
