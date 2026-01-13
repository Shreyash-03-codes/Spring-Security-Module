package com.springboot.testing.repository;

import com.springboot.testing.entity.Session;
import com.springboot.testing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
    Optional<Session> findByRefreshToken(String token);


    List<Session> findAllByUserOrderByLastLoggedInAsc(User user);
}
