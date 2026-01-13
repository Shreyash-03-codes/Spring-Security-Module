package com.security.learn.controller;

import com.security.learn.dto.login.LoginRequestDto;
import com.security.learn.dto.login.LoginResponseDto;
import com.security.learn.dto.response.ApiResponse;
import com.security.learn.dto.signup.SignupRequestDto;
import com.security.learn.dto.signup.SignupResponseDto;
import com.security.learn.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponseDto>> signup(@RequestBody SignupRequestDto signupRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(authService.signup(signupRequestDto)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(authService.login(loginRequestDto)));
    }

}
