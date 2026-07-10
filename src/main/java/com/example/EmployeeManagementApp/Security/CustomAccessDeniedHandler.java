package com.example.EmployeeManagementApp.Security;

import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import org.springframework.http.MediaType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.AccessDeniedException;


@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
       
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            Map<String,Object> body = new HashMap<>();
            String formattedTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            body.put("timestamp", formattedTimestamp);
            body.put("status", HttpServletResponse.SC_FORBIDDEN);
            body.put("error", "Forbidden");
            body.put("message", accessDeniedException.getMessage());
            body.put("path", request.getServletPath());
           
            new ObjectMapper().writeValue(response.getOutputStream(), body);


    }
    
}
