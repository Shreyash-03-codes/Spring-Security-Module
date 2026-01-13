package com.springboot.security.oauth2.service.auth;

import com.springboot.security.oauth2.dto.login.LoginRequestDto;
import com.springboot.security.oauth2.dto.login.LoginResponseDto;
import com.springboot.security.oauth2.dto.response.ApiResponse;
import com.springboot.security.oauth2.dto.signup.SignupRequestDto;
import com.springboot.security.oauth2.dto.signup.SignupResponseDto;
import com.springboot.security.oauth2.entity.User;
import com.springboot.security.oauth2.enums.Role;
import com.springboot.security.oauth2.repository.UserRepository;
import com.springboot.security.oauth2.service.jwt.JwtService;
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

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final ModelMapper modelMapper;



    @Override
    public ApiResponse<SignupResponseDto> signup(SignupRequestDto signupRequestDto) {
        if(userRepository.findByUsername(signupRequestDto.getUsername()).isPresent()){
            throw new RuntimeException("user already exists...");
        }

        User userToSave=modelMapper.map(signupRequestDto,User.class);
        userToSave.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        userToSave.setRole(Role.USER);
        userRepository.save(userToSave);
        SignupResponseDto signupResponseDto=new SignupResponseDto("user created successfully");
        return new ApiResponse<>(signupResponseDto);
    }

    @Override
    public ApiResponse<LoginResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword());
        Authentication authentication=authenticationManager.authenticate(authenticationToken);
        User user=(User) authentication.getPrincipal();

        String accessToken= jwtService.generateAccessToken(user);
        String refreshToken= jwtService.generateRefreshToken(user);

        Cookie cookie=new Cookie("refreshToken",refreshToken);
        cookie.setHttpOnly(true);

        httpServletResponse.addCookie(cookie);
        LoginResponseDto loginResponseDto=new LoginResponseDto(accessToken);
        return new ApiResponse<>(loginResponseDto);
    }

    @Override
    public ApiResponse<LoginResponseDto> refresh(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        Cookie[]cookies=httpServletRequest.getCookies();
        Cookie cookie= Arrays.stream(cookies)
                .filter((c)->c.getName().equals("refreshToken"))
                .findFirst()
                .get();
        if(!jwtService.validateRefreshToken(cookie.getValue())){
            throw new RuntimeException("invalidate the refresh token");
        }

        Long id=Long.parseLong(jwtService.extractUserId(cookie.getValue()));

        User user=userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("username not found.."));


        String accessToken= jwtService.generateAccessToken(user);

        LoginResponseDto loginResponseDto=new LoginResponseDto(accessToken);

        return new ApiResponse<>(loginResponseDto);
    }
}
