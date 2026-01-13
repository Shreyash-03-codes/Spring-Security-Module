package com.springboot.security.refreshtoken.controller;

import com.springboot.security.refreshtoken.dto.datatranmission.ApiResponse;
import com.springboot.security.refreshtoken.dto.datatranmission.UserDto;
import com.springboot.security.refreshtoken.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers(){
        List<UserDto> userDtos=userService.getAllUsers();
        ApiResponse<List<UserDto>> apiResponse=new ApiResponse<>(userDtos);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
