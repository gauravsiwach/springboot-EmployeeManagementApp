package com.example.EmployeeManagementApp.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;


@Configuration
public class SecurityConfig {
     @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("SecurityConfig Loaded");
        
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.GET, "/employees", "/employees/**").permitAll()
            .requestMatchers(HttpMethod.PUT,"/employees/**").permitAll()
            .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults());
            
        return http.build();
    }
}
