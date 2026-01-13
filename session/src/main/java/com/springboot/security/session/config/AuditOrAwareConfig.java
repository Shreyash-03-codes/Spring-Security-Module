package com.springboot.security.session.config;

import com.springboot.security.session.service.auditoraware.AuditOrAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class AuditOrAwareConfig  {

    @Bean
    public AuditorAware<String> auditorAware(){
        return new AuditOrAwareImpl();
    }

}
