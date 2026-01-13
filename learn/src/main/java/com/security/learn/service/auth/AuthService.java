package com.security.learn.service.auth;

import com.security.learn.dto.login.LoginRequestDto;
import com.security.learn.dto.login.LoginResponseDto;
import com.security.learn.dto.signup.SignupRequestDto;
import com.security.learn.dto.signup.SignupResponseDto;

public interface AuthService {
    SignupResponseDto signup(SignupRequestDto signupRequestDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
}
