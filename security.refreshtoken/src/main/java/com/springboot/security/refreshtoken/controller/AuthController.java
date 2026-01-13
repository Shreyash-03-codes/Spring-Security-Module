package com.springboot.security.refreshtoken.controller;

import com.springboot.security.refreshtoken.dto.datatranmission.ApiResponse;
import com.springboot.security.refreshtoken.dto.login.LoginRequestDto;
import com.springboot.security.refreshtoken.dto.login.LoginResponseDto;
import com.springboot.security.refreshtoken.dto.signup.SignupRequestDto;
import com.springboot.security.refreshtoken.dto.signup.SignupResponseDto;
import com.springboot.security.refreshtoken.service.auth.AuthService;
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
@RequestMapping("/public/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private  final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse){
        LoginResponseDto loginResponseDto=authService.login(loginRequestDto,httpServletResponse);
        ApiResponse<LoginResponseDto> apiResponse=new ApiResponse<>(loginResponseDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponseDto>> signup(@RequestBody SignupRequestDto signupRequestDto){
        SignupResponseDto signupResponseDto=authService.signup(signupRequestDto);
        ApiResponse<SignupResponseDto> apiResponse=new ApiResponse<>(signupResponseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponseDto>> refresh(HttpServletRequest httpServletRequest){
        LoginResponseDto loginResponseDto=authService.refresh(httpServletRequest);
        ApiResponse<LoginResponseDto> apiResponse=new ApiResponse<>(loginResponseDto);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
