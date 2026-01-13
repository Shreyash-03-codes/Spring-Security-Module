package com.security.learn.enums;

import lombok.Getter;

import java.util.Set;

@Getter
public enum Role {

    ADMIN(Set.of(Permissions.CREATE,Permissions.READ,Permissions.UPDATE,Permissions.DELETE,Permissions.APPROVE)),
    USER(Set.of(Permissions.READ));

    public final Set<Permissions> permissions;

    Role(Set<Permissions>permissions){
        this.permissions=permissions;
    }
}
