package com.springboot.testing.dto.signup;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    private String name;

    private String username;

    private String password;

    private String email;

    @JsonProperty("address")
    private AddressDto addressDto;

}
