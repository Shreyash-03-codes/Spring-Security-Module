package com.springboot.security.refreshtoken.service.user;

import com.springboot.security.refreshtoken.dto.datatranmission.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
}
