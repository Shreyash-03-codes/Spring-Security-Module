package com.springboot.security.refreshtoken.service.user;

import com.springboot.security.refreshtoken.dto.datatranmission.UserDto;
import com.springboot.security.refreshtoken.repository.UserRepository;
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
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map((u)-> modelMapper.map(u,UserDto.class))
                .toList();
    }
}
