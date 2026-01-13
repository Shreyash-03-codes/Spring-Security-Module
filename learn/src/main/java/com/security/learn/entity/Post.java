package com.security.learn.entity;

import com.security.learn.entity.audit.AuditSuperClass;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "posts")
@RequiredArgsConstructor
@Getter
@Setter
public class Post extends AuditSuperClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

}

