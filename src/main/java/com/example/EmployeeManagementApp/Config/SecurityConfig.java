package com.example.EmployeeManagementApp.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//     @Bean
//     public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//             UserDetails admin =User.builder()
//                     .username("admin")
//                     .password(encoder.encode("admin123"))
//                     .roles("ADMIN")
//                     .build();
            
//             UserDetails user =User.builder()
//                     .username("user")
//                     .password(encoder.encode("user123"))
//                     .roles("USER")
//                     .build();

//             return new InMemoryUserDetailsManager(admin, user);
         
//     }

     @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("SecurityConfig Loaded");

         http.csrf(csrf -> csrf.disable())
                 .authorizeHttpRequests(auth -> auth

                         // Swagger
                         .requestMatchers(
                                 "/swagger-ui/**",
                                 "/swagger-ui.html",
                                 "/v3/api-docs/**"
                         ).permitAll()

                         // Registration
                         .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

                         // Employee APIs
                         .requestMatchers(HttpMethod.GET, "/employees", "/employees/**")
                         .hasAnyRole("ADMIN", "USER")
                         .requestMatchers(HttpMethod.POST, "/employees")
                         .hasRole("ADMIN")
                         .requestMatchers(HttpMethod.PUT, "/employees/**")
                         .hasRole("ADMIN")
                         .requestMatchers(HttpMethod.DELETE, "/employees/**")
                         .hasRole("ADMIN")

                         .anyRequest().authenticated()
                 )
                 .httpBasic(Customizer.withDefaults());
            
        return http.build();
    }
}
