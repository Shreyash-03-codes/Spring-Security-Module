package com.springboot.security.oauth2.service.auth;

import com.springboot.security.oauth2.dto.login.LoginRequestDto;
import com.springboot.security.oauth2.dto.login.LoginResponseDto;
import com.springboot.security.oauth2.dto.response.ApiResponse;
import com.springboot.security.oauth2.dto.signup.SignupRequestDto;
import com.springboot.security.oauth2.dto.signup.SignupResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    ApiResponse<SignupResponseDto> signup(SignupRequestDto signupRequestDto);

    ApiResponse<LoginResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse);

    ApiResponse<LoginResponseDto> refresh(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
