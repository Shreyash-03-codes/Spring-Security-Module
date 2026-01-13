package com.security.learn.dto.login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class LoginRequestDto {
    private String username;
    private String password;
}
