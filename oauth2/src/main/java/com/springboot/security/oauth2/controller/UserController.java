package com.springboot.security.oauth2.controller;

import com.springboot.security.oauth2.dto.response.ApiResponse;
import com.springboot.security.oauth2.dto.signup.SignupRequestDto;
import com.springboot.security.oauth2.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<SignupRequestDto>>> allUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.allUsers());
    }
}
