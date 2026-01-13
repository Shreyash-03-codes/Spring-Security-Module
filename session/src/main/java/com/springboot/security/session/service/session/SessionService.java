package com.springboot.security.session.service.session;

import com.springboot.security.session.dto.login.LoginResponseDto;
import com.springboot.security.session.entity.User;
import jakarta.servlet.http.HttpServletResponse;

public interface SessionService {
    LoginResponseDto login(User user, HttpServletResponse response);
    boolean validateRefreshToken(String refreshToken);
}
