package com.springboot.security.refreshtoken.enums;

import java.util.Set;

public enum Role {
    USER(Set.of(Permission.CREATE,Permission.READ,Permission.UPDATE,Permission.DELETE)),
    ADMIN(Set.of(Permission.CREATE,Permission.READ,Permission.UPDATE,Permission.DELETE,Permission.REJECT,Permission.APPROVE,Permission.DENNIE));

    public final Set<Permission> permissions;

    Role(Set<Permission> permissions){
        this.permissions=permissions;
    }

}
