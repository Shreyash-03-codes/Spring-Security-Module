package com.springboot.testing.entity;

import com.springboot.testing.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Embedded
    private Address address;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<SimpleGrantedAuthority> authorities=new HashSet<>();
        roles.forEach((r)-> {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + r.name()));
                    authorities.addAll(
                            r.getPermissions()
                                    .stream()
                                    .map((p)->
                                            new SimpleGrantedAuthority(p.name())
                                    )
                                    .collect(Collectors.toSet())
                    );
                }
        );
        return authorities;
    }
}
