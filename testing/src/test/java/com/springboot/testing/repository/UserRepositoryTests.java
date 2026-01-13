package com.springboot.testing.repository;

import com.springboot.testing.entity.Address;
import com.springboot.testing.entity.User;
import com.springboot.testing.enums.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Set;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest

public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    public void setUp(){

        sessionRepository.deleteAll();
        userRepository.deleteAll();

        Address address=Address.builder()
                .pin("413604")
                .street("Salegaon road")
                .city("Umerga")
                .build();

        User user=User.builder()
                .name("shreyash")
                .username("shreyash")
                .password("shreyash@2005")
                .email("shreyash@gmail.com")
                .roles(Set.of(Role.USER, Role.ADMIN))
                .address(address)
                .build();
        userRepository.save(user);
    }

    @Test
    public void testFindByUsername_whenUsernameIdValid_thenOptionalOfUserGets(){
        //arrange


        //act
        Optional<User> user=userRepository.findByUsername("shreyash");

        //assert
        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user).isInstanceOf(Optional.class);
        Assertions.assertThat(user).isPresent();
    }

    @Test
    public void testFindByUsername_whenUsernameIsInValid_thenEmptyOptionalOfUserGets(){
        //arrange

        //act
        Optional<User> user=userRepository.findByUsername("");

        //assert

        Assertions.assertThat(user).isNotPresent();
        Assertions.assertThat(user).isEmpty();
    }
}
