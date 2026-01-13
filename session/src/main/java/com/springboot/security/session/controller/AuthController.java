package com.springboot.security.session.controller;


import com.springboot.security.session.dto.login.LoginRequestDto;
import com.springboot.security.session.dto.login.LoginResponseDto;
import com.springboot.security.session.dto.response.ApiResponse;
import com.springboot.security.session.dto.signup.SignupRequestDto;
import com.springboot.security.session.dto.signup.SignupResponseDto;
import com.springboot.security.session.service.auth.AuthService;
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
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequestDto,response));
    }
    @GetMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponseDto>> refresh(HttpServletRequest request,HttpServletResponse response){
        return ResponseEntity.status(HttpStatus.OK).body(authService.refresh(request,response));
    }
}
