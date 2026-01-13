package com.springboot.security.session.service.session;

import com.springboot.security.session.dto.login.LoginResponseDto;
import com.springboot.security.session.entity.Session;
import com.springboot.security.session.entity.User;
import com.springboot.security.session.repository.SessionRepository;
import com.springboot.security.session.service.jwt.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService{

    private final JwtService jwtService;

    private final SessionRepository sessionRepository;

    private final Integer SESSION_LIMIT=2;

    @Override
    public LoginResponseDto login(User user, HttpServletResponse response) {

        List<Session> sessions=sessionRepository.findAllByUserOrderByLastLoggedInAsc(user);

        if(sessions.size()>=SESSION_LIMIT){
            Session session=sessions.get(0);
            sessionRepository.delete(session);
        }

        String accessToken= jwtService.generateAccessToken(user);
        String refreshToken=jwtService.generateRefreshToken(user);
        Cookie cookie=new Cookie("refreshToken",refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        Session session=new Session();
        session.setUser(user);
        session.setRefreshToken(refreshToken);
        session.setLastLoggedIn(LocalDateTime.now());
        sessionRepository.save(session);
        return new LoginResponseDto(accessToken,user.getUsername(),user.getName());
    }

    public boolean validateRefreshToken(String refreshToken){
        return sessionRepository.findByRefreshToken(refreshToken).isPresent();
    }
}
