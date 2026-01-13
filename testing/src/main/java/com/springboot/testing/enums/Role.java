package com.springboot.testing.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN(Set.of(Permissions.CREATE,Permissions.DELETE,Permissions.READ,Permissions.UPDATE)),
    USER(Set.of(Permissions.READ));

    private final Set<Permissions> permissions;

}
