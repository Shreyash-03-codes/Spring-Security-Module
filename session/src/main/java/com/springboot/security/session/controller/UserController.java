package com.springboot.security.session.controller;

import com.springboot.security.session.dto.response.ApiResponse;
import com.springboot.security.session.dto.user.UserResponseDto;
import com.springboot.security.session.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
    }
}
