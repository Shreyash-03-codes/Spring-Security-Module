package com.springboot.security.session.service.user;

import com.springboot.security.session.dto.response.ApiResponse;
import com.springboot.security.session.dto.user.UserResponseDto;
import com.springboot.security.session.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;


    @Override
    public ApiResponse<List<UserResponseDto>> getAllUser() {
        return new ApiResponse<>( userRepository.findAll()
                .stream()
                .map((u)->modelMapper.map(u, UserResponseDto.class))
                .toList());
    }
}
