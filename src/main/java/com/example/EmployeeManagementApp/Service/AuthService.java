package com.example.EmployeeManagementApp.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EmployeeManagementApp.Dto.RegisterRequest;
import com.example.EmployeeManagementApp.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.EmployeeManagementApp.Entity.AppUser;
import com.example.EmployeeManagementApp.Entity.Role;


@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;


    public String register(RegisterRequest registerRequest) {
       
        if(userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        AppUser user = new AppUser();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.ROLE_USER);
        userRepository.save(user);
        return "User registered successfully";
    }

}
