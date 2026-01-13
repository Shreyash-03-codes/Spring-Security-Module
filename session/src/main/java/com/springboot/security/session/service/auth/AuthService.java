package com.springboot.security.session.service.auth;

import com.springboot.security.session.dto.login.LoginRequestDto;
import com.springboot.security.session.dto.login.LoginResponseDto;
import com.springboot.security.session.dto.response.ApiResponse;
import com.springboot.security.session.dto.signup.SignupRequestDto;
import com.springboot.security.session.dto.signup.SignupResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    ApiResponse<SignupResponseDto> signup(SignupRequestDto signupRequestDto);

    ApiResponse<LoginResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response);

    ApiResponse<LoginResponseDto> refresh(HttpServletRequest request, HttpServletResponse response);
}
