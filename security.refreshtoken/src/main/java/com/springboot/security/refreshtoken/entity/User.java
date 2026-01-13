package com.springboot.security.refreshtoken.entity;

import com.springboot.security.refreshtoken.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<SimpleGrantedAuthority> authorities=new HashSet<>();
        authorities.addAll(this.role.permissions
                .stream()
                .map((p)->new SimpleGrantedAuthority(p.name()))
                .collect(Collectors.toSet()));
        SimpleGrantedAuthority simpleGrantedAuthorityRole=new SimpleGrantedAuthority("ROLE_"+this.role.name());
        authorities.add(simpleGrantedAuthorityRole);

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
