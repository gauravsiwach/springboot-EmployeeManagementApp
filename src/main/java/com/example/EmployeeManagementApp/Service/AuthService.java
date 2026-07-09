package com.example.EmployeeManagementApp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.example.EmployeeManagementApp.Dto.LoginRequest;
import com.example.EmployeeManagementApp.Dto.LoginResponse;

import com.example.EmployeeManagementApp.Dto.RegisterRequest;
import com.example.EmployeeManagementApp.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.EmployeeManagementApp.Entity.AppUser;
import com.example.EmployeeManagementApp.Entity.Role;
import org.springframework.security.core.Authentication;
import com.example.EmployeeManagementApp.Security.JwtService;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;


    public String register(RegisterRequest registerRequest) {

        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        AppUser user = new AppUser();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(registerRequest.getRole());
        userRepository.save(user);
        return "User registered successfully";
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        return new LoginResponse(token);

    }
}
