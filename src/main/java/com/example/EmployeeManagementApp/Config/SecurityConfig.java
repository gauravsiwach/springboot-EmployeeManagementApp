package com.example.EmployeeManagementApp.Config;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.EmployeeManagementApp.Security.CustomAccessDeniedHandler;
import com.example.EmployeeManagementApp.Security.JwtAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.EmployeeManagementApp.Security.JwtAuthenticationFilter;

 
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

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
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

     @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("SecurityConfig Loaded");

        //  http.csrf(csrf -> csrf.disable())
        //          .authorizeHttpRequests(auth -> auth

        //                  // Swagger
        //                  .requestMatchers(
        //                          "/swagger-ui/**",
        //                          "/swagger-ui.html",
        //                          "/v3/api-docs/**"
        //                  ).permitAll()

        //                  // Registration, Login
        //                  .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
        //                  .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

        //                  // Employee APIs
        //                  .requestMatchers(HttpMethod.GET, "/employees", "/employees/**")
        //                  .hasAnyRole("ADMIN", "USER")
        //                  .requestMatchers(HttpMethod.POST, "/employees")
        //                  .hasRole("ADMIN")
        //                  .requestMatchers(HttpMethod.PUT, "/employees/**")
        //                  .hasRole("ADMIN")
        //                  .requestMatchers(HttpMethod.DELETE, "/employees/**")
        //                  .hasRole("ADMIN")

        //                  .anyRequest().authenticated()
        //          )
        //          .httpBasic(Customizer.withDefaults());
            
        // return http.build();


        //  http.csrf(csrf -> csrf.disable())
        //          .authorizeHttpRequests(auth -> auth

        //                  // Swagger
        //                  .requestMatchers(
        //                          "/swagger-ui/**",
        //                          "/swagger-ui.html",
        //                          "/v3/api-docs/**"
        //                  ).permitAll()

        //                  // Registration, Login
        //                  .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
        //                  .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

        //                  // Employee APIs
        //                  .requestMatchers(HttpMethod.GET, "/employees", "/employees/**")
        //                  .hasAnyRole("ADMIN", "USER")
        //                  .requestMatchers(HttpMethod.POST, "/employees")
        //                  .hasRole("ADMIN")
        //                  .requestMatchers(HttpMethod.PUT, "/employees/**")
        //                  .hasRole("ADMIN")
        //                  .requestMatchers(HttpMethod.DELETE, "/employees/**")
        //                  .hasRole("ADMIN")

        //                  .anyRequest().authenticated()
        //          )
        //          .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        //          .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            
        // return http.build();

        http.csrf(csrf -> csrf.disable())
                 .authorizeHttpRequests(auth -> auth

                         // Swagger
                         .requestMatchers(
                                 "/swagger-ui/**",
                                 "/swagger-ui.html",
                                 "/v3/api-docs/**"
                         ).permitAll()

                         // Registration, Login
                         .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                         .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()

                         // Employee APIs
                         .anyRequest().authenticated()
                 )
                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .exceptionHandling(exception -> 
                    exception.authenticationEntryPoint(authenticationEntryPoint)
                    .accessDeniedHandler(customAccessDeniedHandler))
                 .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }
}
