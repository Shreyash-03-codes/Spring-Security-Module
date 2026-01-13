package com.springboot.security.refreshtoken.service.auditoraware;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;


public class AuditOrAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {

        if(SecurityContextHolder.getContext().getAuthentication()!=null &&  !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){
            return Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());

        }
        return Optional.of("SYSTEM");
    }
}
