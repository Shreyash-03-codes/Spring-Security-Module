package com.springboot.security.session.enums;


import java.util.Set;

public enum Role {
    ADMIN(Set.of(Permissions.CREATE,Permissions.DELETE,Permissions.READ,Permissions.UPDATE)),
    USER(Set.of(Permissions.READ));

    private Set<Permissions> permissions;

    Role(Set<Permissions> permissions){
        this.permissions=permissions;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }
}
