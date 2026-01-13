package com.springboot.security.oauth2.service.user;


import com.springboot.security.oauth2.dto.response.ApiResponse;
import com.springboot.security.oauth2.dto.signup.SignupRequestDto;

import java.util.List;

public interface UserService {
    ApiResponse<List<SignupRequestDto>> allUsers();
}
