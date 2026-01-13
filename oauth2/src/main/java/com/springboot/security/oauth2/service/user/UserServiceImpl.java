package com.springboot.security.oauth2.service.user;

import com.springboot.security.oauth2.dto.response.ApiResponse;
import com.springboot.security.oauth2.dto.signup.SignupRequestDto;
import com.springboot.security.oauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse<List<SignupRequestDto>> allUsers() {
        return new ApiResponse<>( userRepository.findAll()
                .stream()
                .map((u)->modelMapper.map(u,SignupRequestDto.class))
                .toList());
    }
}
