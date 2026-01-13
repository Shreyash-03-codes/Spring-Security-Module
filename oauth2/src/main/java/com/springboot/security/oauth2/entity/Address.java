package com.springboot.security.oauth2.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Embeddable
@RequiredArgsConstructor
@Setter
@Getter
public class Address {
    private String houseNo;
    private String street;
    private String city;
}
