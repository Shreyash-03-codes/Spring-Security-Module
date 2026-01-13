package com.security.learn.service.auth;

import com.security.learn.dto.login.LoginRequestDto;
import com.security.learn.dto.login.LoginResponseDto;
import com.security.learn.dto.signup.SignupRequestDto;
import com.security.learn.dto.signup.SignupResponseDto;
import com.security.learn.entity.User;
import com.security.learn.enums.Role;
import com.security.learn.repository.UserRepository;
import com.security.learn.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final ModelMapper modelMapper;

    public SignupResponseDto signup(SignupRequestDto signupRequestDto){
        if(userRepository.findByUsername(signupRequestDto.getUsername()).isPresent()){
            throw new IllegalStateException("User already present");
        }
        User userToSave=modelMapper.map(signupRequestDto,User.class);
        userToSave.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        userToSave.setRole(Role.USER);
        User savedUser=this.userRepository.save(userToSave);
        return new SignupResponseDto("New User Created");
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        Authentication authenticated = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword())
        );
        User user=(User) authenticated.getPrincipal();
        String token=jwtService.generateToken(user);
        return new LoginResponseDto(token);
    }


}
