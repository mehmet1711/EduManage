package com.example.demo.config;

import com.example.demo.student.StudentDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private final StudentDetailsService studentDetailsService;

    @Autowired
    public CustomLogoutSuccessHandler(StudentDetailsService studentDetailsService) {
        this.studentDetailsService = studentDetailsService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        if (authentication != null && authentication.getName() != null) {
            String email = authentication.getName();
            studentDetailsService.setInactiveStatus();
        }
        response.sendRedirect("/login?logout"); // Logout başarılı olduktan sonra yönlendirme
    }
}
