package com.springboot.security.refreshtoken.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequestDto {

    private String username;

    private String password;
}
