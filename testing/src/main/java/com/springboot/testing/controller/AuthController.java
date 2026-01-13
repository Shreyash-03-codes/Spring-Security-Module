package com.springboot.testing.controller;

import com.springboot.testing.dto.login.LoginRequestDto;
import com.springboot.testing.dto.login.LoginResponseDto;
import com.springboot.testing.dto.refresh.RefreshResponseDto;
import com.springboot.testing.dto.response.ApiResponse;
import com.springboot.testing.dto.signup.SignupRequestDto;
import com.springboot.testing.dto.signup.SignupResponseDto;
import com.springboot.testing.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponseDto>> signup(@RequestBody SignupRequestDto signupRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(signupRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request, HttpServletResponse response){
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequestDto,request,response));
    }

    @GetMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshResponseDto>> refresh(HttpServletRequest request,HttpServletResponse response){
        return ResponseEntity.status(HttpStatus.OK).body(authService.refresh(request,response));
    }
}
