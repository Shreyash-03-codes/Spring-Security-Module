package com.springboot.testing.config;

import com.springboot.testing.service.auditoraware.AuditOrAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditAwareConfig {

    @Bean
    public AuditOrAwareImpl auditorAware(){
        return new AuditOrAwareImpl();
    }
}
