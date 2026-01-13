package com.springboot.security.session.service.auth;

import com.springboot.security.session.dto.login.LoginRequestDto;
import com.springboot.security.session.dto.login.LoginResponseDto;
import com.springboot.security.session.dto.response.ApiResponse;
import com.springboot.security.session.dto.signup.SignupRequestDto;
import com.springboot.security.session.dto.signup.SignupResponseDto;
import com.springboot.security.session.entity.Session;
import com.springboot.security.session.entity.User;
import com.springboot.security.session.enums.Role;
import com.springboot.security.session.repository.UserRepository;
import com.springboot.security.session.service.jwt.JwtService;
import com.springboot.security.session.service.session.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final SessionService sessionService;

    @Override
    public ApiResponse<SignupResponseDto> signup(SignupRequestDto signupRequestDto) {

        if(userRepository.findByUsername(signupRequestDto.getUsername()).isPresent()){
            throw  new IllegalStateException("user already exists");
        }
        User userToSave=modelMapper.map(signupRequestDto,User.class);
        userToSave.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        userToSave.setRoles(Set.of(Role.USER));
        userRepository.save(userToSave);
        return new ApiResponse<>(new SignupResponseDto("User created successfully"));
    }

    @Override
    public ApiResponse<LoginResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {

        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword()));
        User user=(User) authentication.getPrincipal();
        LoginResponseDto loginResponseDto=sessionService.login(user,response);
        return new ApiResponse<>(loginResponseDto);
    }

    @Override
    public ApiResponse<LoginResponseDto> refresh(HttpServletRequest request,HttpServletResponse response) {

        Cookie[]cookies=request.getCookies();
        String refreshToken= Arrays.stream(cookies)
                .filter((c)->c.getName().equals("refreshToken"))
                .findFirst()
                .get()
                .getValue();

        if(!jwtService.validateRefreshToken(refreshToken) && !sessionService.validateRefreshToken(refreshToken)){
            throw new IllegalStateException("refresh token invalidate");
        }
        Long id=Long.parseLong(jwtService.extractIdFromRefreshToken(refreshToken));
        User user=userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("user not found.."));
        String accessToken= jwtService.generateAccessToken(user);
        String newRefreshToken=jwtService.generateRefreshToken(user);
        Cookie cookie=new Cookie("refreshToken",newRefreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/v1/auth/refresh");
        return new ApiResponse<>(new LoginResponseDto(accessToken,user.getUsername(),user.getName()));
    }
}
