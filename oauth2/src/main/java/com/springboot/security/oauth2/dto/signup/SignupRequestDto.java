package com.springboot.security.oauth2.dto.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    private String name;
    private String username;
    private String password;
    private AddressDto addressDto;
}
