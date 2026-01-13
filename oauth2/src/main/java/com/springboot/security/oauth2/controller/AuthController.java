package com.springboot.security.oauth2.controller;

import com.springboot.security.oauth2.dto.login.LoginRequestDto;
import com.springboot.security.oauth2.dto.login.LoginResponseDto;
import com.springboot.security.oauth2.dto.response.ApiResponse;
import com.springboot.security.oauth2.dto.signup.SignupRequestDto;
import com.springboot.security.oauth2.dto.signup.SignupResponseDto;
import com.springboot.security.oauth2.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/public/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponseDto>> signup(@RequestBody SignupRequestDto signupRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(signupRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse){
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequestDto,httpServletResponse));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponseDto>> refresh(HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest){
        return ResponseEntity.status(HttpStatus.OK).body(authService.refresh(httpServletRequest,httpServletResponse));
    }


}
