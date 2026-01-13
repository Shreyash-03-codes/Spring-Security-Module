package com.springboot.security.session.repository;

import com.springboot.security.session.entity.Session;
import com.springboot.security.session.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
    List<Session> findAllByUserOrderByLastLoggedInAsc(User user);


    Optional<Session> findByRefreshToken(String refreshToken);
}
