package com.springboot.testing.service.session;

import com.springboot.testing.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface SessionService {
     void validateRefreshToken(HttpServletRequest request, User user);
     void checkAlreadySessionExists(HttpServletRequest request,User user);
    String getRefreshToken(HttpServletRequest request);
    void createSession(User user,String refreshToken);

}
