package com.springboot.security.oauth2.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN(Set.of(Permissions.CREATE,Permissions.UPDATE,Permissions.READ,Permissions.DELETE,Permissions.REJECT,Permissions.DENNIE,Permissions.APPROVE,Permissions.ACCEPT)),
    USER(Set.of(Permissions.CREATE,Permissions.UPDATE,Permissions.READ,Permissions.DELETE));


    private final Set<Permissions> permissionsSet;



}
