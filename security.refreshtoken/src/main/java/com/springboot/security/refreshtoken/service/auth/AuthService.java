package com.springboot.security.refreshtoken.service.auth;

import com.springboot.security.refreshtoken.dto.login.LoginRequestDto;
import com.springboot.security.refreshtoken.dto.login.LoginResponseDto;
import com.springboot.security.refreshtoken.dto.signup.SignupRequestDto;
import com.springboot.security.refreshtoken.dto.signup.SignupResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response);
    SignupResponseDto signup(SignupRequestDto signupRequestDto);
    LoginResponseDto refresh(HttpServletRequest request);
}
