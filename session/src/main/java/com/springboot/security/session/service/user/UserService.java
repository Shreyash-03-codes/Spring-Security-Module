package com.springboot.security.session.service.user;

import com.springboot.security.session.dto.response.ApiResponse;
import com.springboot.security.session.dto.user.UserResponseDto;

import java.util.List;

public interface UserService {
    ApiResponse<List<UserResponseDto>> getAllUser();
}
