package com.springboot.testing.service.auth;

import com.springboot.testing.dto.login.LoginRequestDto;
import com.springboot.testing.dto.login.LoginResponseDto;
import com.springboot.testing.dto.refresh.RefreshResponseDto;
import com.springboot.testing.dto.response.ApiResponse;
import com.springboot.testing.dto.signup.SignupRequestDto;
import com.springboot.testing.dto.signup.SignupResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    ApiResponse<SignupResponseDto> signup(SignupRequestDto signupRequestDto);

    ApiResponse<LoginResponseDto> login(LoginRequestDto loginRequestDto, HttpServletRequest request, HttpServletResponse response);

    ApiResponse<RefreshResponseDto> refresh(HttpServletRequest request, HttpServletResponse response);
}
