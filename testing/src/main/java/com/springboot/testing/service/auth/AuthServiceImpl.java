package com.springboot.testing.service.auth;

import com.springboot.testing.dto.login.LoginRequestDto;
import com.springboot.testing.dto.login.LoginResponseDto;
import com.springboot.testing.dto.refresh.RefreshResponseDto;
import com.springboot.testing.dto.response.ApiResponse;
import com.springboot.testing.dto.signup.SignupRequestDto;
import com.springboot.testing.dto.signup.SignupResponseDto;
import com.springboot.testing.entity.User;
import com.springboot.testing.enums.Role;
import com.springboot.testing.repository.UserRepository;
import com.springboot.testing.service.jwt.JwtService;
import com.springboot.testing.service.session.SessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final SessionService sessionService;

    @Override
    public ApiResponse<SignupResponseDto> signup(SignupRequestDto signupRequestDto) {

        if(userRepository.findByUsername(signupRequestDto.getUsername()).isPresent()){
            throw new IllegalStateException("user already exists");
        }
        User user=modelMapper.map(signupRequestDto,User.class);
        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        user.setRoles(Set.of(Role.USER));
        userRepository.save(user);
        SignupResponseDto signupResponseDto=new SignupResponseDto("User created successfully");
        return new ApiResponse<>(signupResponseDto);
    }

    @Override
    public ApiResponse<LoginResponseDto> login(LoginRequestDto loginRequestDto, HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword()));
        User user=(User) authentication.getPrincipal();
        sessionService.checkAlreadySessionExists(request,user);
        String accessToken=jwtService.generateAccessToken(user);
        String refreshToken=jwtService.generateRefreshToken(user);
        sessionService.createSession(user,refreshToken);
        LoginResponseDto loginResponseDto=new LoginResponseDto(user.getName(),user.getUsername(),accessToken);
        Cookie cookie=new Cookie("refreshToken",refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return new ApiResponse<>(loginResponseDto);
    }

    @Override
    public ApiResponse<RefreshResponseDto> refresh(HttpServletRequest request, HttpServletResponse response) {
        String token=sessionService.getRefreshToken(request);
        if(!jwtService.validateRefreshToken(token)){
            throw new IllegalStateException("invalidate token");
        }
        Long id=Long.parseLong(jwtService.extractUserId(token));
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("user not found"));
        sessionService.validateRefreshToken(request,user);
        String accessToken=jwtService.generateAccessToken(user);
        RefreshResponseDto refreshResponseDto=new RefreshResponseDto(accessToken);


        return new ApiResponse<>(refreshResponseDto);
    }
}
