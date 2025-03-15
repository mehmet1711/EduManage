package com.example.demo.config;

import com.example.demo.student.StudentDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final StudentDetailsService studentDetailsService;

    public CustomAuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider,
                                              StudentDetailsService studentDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.studentDetailsService = studentDetailsService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        List<String> roles = studentDetailsService.getRoles(username);
        String token = jwtTokenProvider.createToken(username, roles);


        studentDetailsService.setActiveStatus();

        // Yanıt ayarları
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("{\"token\": \"" + token + "\"}");
        response.getWriter().flush();
    }
}
