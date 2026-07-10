package com.example.EmployeeManagementApp.Security;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.http.MediaType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.AuthenticationException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;

@Component
public class JwtAuthenticationEntryPoint implements  AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Map<String, Object> responseBody = new HashMap<>();
         // Readable date-time format
        String formattedTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        responseBody.put("timestamp", formattedTimestamp);
        responseBody.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        responseBody.put("error", "Unauthorized");
        responseBody.put("message", authException.getMessage());
        responseBody.put("path", request.getRequestURI());

        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
       

    }
}
