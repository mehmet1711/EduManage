package com.example.demo.student;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class DataInitializer {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        for (Student student : studentRepository.findAll()) {
            if (!isPasswordEncoded(student.getPassword())) {
                String encodedPassword = passwordEncoder.encode(student.getPassword());
                student.setPassword(encodedPassword);
                studentRepository.save(student);
            }

        }
    }

    private boolean isPasswordEncoded(String password) {
        return password.length() > 60;
    }
}
