package com.example.EmployeeManagementApp.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.lang.reflect.AnnotatedArrayType;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class AuditorAwareImpl {

    @Bean
    public AuditorAware<String> auditorProvider() 
    {
        return ()->{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnnotatedArrayType) {
                return Optional.of("System");
            } 
            return Optional.of(authentication.getName());
        };
    }

}
