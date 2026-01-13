package com.springboot.testing.service.session;

import com.springboot.testing.entity.Session;
import com.springboot.testing.entity.User;
import com.springboot.testing.repository.SessionRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    private final Integer MAX_SESSIONS=2;

    public void validateRefreshToken(HttpServletRequest request, User user){
        String token=getRefreshToken(request);
        Session session=sessionRepository.findByRefreshToken(token).orElseThrow(()->new RuntimeException("Refresh token is not valid"));
        User sessionUser=session.getUser();
        if(!user.getUsername().equals(sessionUser.getUsername())){
            throw new RuntimeException("Refresh token is not valid");
        }
    }

    public void checkAlreadySessionExists(HttpServletRequest request,User user){
        List<Session> sessions=sessionRepository.findAllByUserOrderByLastLoggedInAsc(user);
        if(sessions.size()>=MAX_SESSIONS){
            sessionRepository.delete(sessions.get(0));
        }

    }


    public String getRefreshToken(HttpServletRequest request){

        Cookie[]cookies=request.getCookies();
        return Arrays.stream(cookies)
                .filter((c)->c.getName().equals("refreshToken"))
                .findFirst()
                .get()
                .getValue();
    }

    public void createSession(User user,String refreshToken){
        Session session=new Session();
        session.setUser(user);
        session.setLastLoggedIn(LocalDateTime.now());
        session.setRefreshToken(refreshToken);
        sessionRepository.save(session);
    }
}
