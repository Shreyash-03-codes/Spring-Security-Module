package com.security.learn.entity;

import com.security.learn.entity.audit.AuditSuperClass;
import com.security.learn.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class User  extends AuditSuperClass implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority role=new SimpleGrantedAuthority("ROLE_"+this.role.name());
        List<SimpleGrantedAuthority> auths=new ArrayList<>();
                auths.addAll(this.role.getPermissions()
                .stream()
                .map(p->new SimpleGrantedAuthority(p.name()))
                .toList());

        auths.add(role);

        return auths;
    }
}
