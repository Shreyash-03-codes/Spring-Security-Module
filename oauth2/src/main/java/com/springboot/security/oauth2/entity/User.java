package com.springboot.security.oauth2.entity;

import com.springboot.security.oauth2.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;

    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities=new HashSet<>();
        SimpleGrantedAuthority authRole=new SimpleGrantedAuthority("ROLE_"+this.role.name());
        authorities.add(authRole);
        authorities.addAll(
                this.role.getPermissionsSet()
                        .stream()
                        .map((p)->new SimpleGrantedAuthority(p.name()))
                        .collect(Collectors.toSet())
        );
        return authorities;
    }
}
