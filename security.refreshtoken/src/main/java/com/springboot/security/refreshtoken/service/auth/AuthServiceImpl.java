package com.springboot.security.refreshtoken.service.auth;

import com.springboot.security.refreshtoken.dto.login.LoginRequestDto;
import com.springboot.security.refreshtoken.dto.login.LoginResponseDto;
import com.springboot.security.refreshtoken.dto.signup.SignupRequestDto;
import com.springboot.security.refreshtoken.dto.signup.SignupResponseDto;
import com.springboot.security.refreshtoken.entity.User;
import com.springboot.security.refreshtoken.enums.Role;
import com.springboot.security.refreshtoken.repository.UserRepository;
import com.springboot.security.refreshtoken.service.jwt.JwtService;
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

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword());
        Authentication authenticated=authenticationManager.authenticate(authenticationToken);
        User user=(User) authenticated.getPrincipal();
        String accessToken=jwtService.generateAccessToken(user);
        String refreshToken=jwtService.generateRefreshToken(user);
        Cookie cookie=new Cookie("refreshToken",refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        LoginResponseDto loginResponseDto=new LoginResponseDto(user.getName(),accessToken);
        return loginResponseDto;
    }

    @Override
    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        if(userRepository.findByUsername(signupRequestDto.getUsername()).isPresent()){
            throw new IllegalStateException("user already exists");
        }

        User userToSave=modelMapper.map(signupRequestDto,User.class);
        userToSave.setRole(Role.USER);
        userToSave.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        userRepository.save(userToSave);

        return new SignupResponseDto("User registered successfully..");
    }

    @Override
    public LoginResponseDto refresh(HttpServletRequest request) {
        Cookie[]cookies=request.getCookies();
        String refreshToken=Arrays.stream(cookies)
                .filter((c)->c.getName().equals("refreshToken"))
                .findFirst()
                .get()
                .getValue();

        if(!jwtService.validateRefreshToken(refreshToken)){
            throw new RuntimeException("Token is now invalid");
        }
        System.out.println(refreshToken);

        Long id=Long.parseLong(jwtService.extractId(refreshToken));
        User user=userRepository.findById(id).get();
        String accessToken=jwtService.generateAccessToken(user);
        return new LoginResponseDto(user.getName(),accessToken);
    }
}
