package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    public CommandLineRunner commandLineRunner(StudentRepository repository,
                                               BCryptPasswordEncoder bCryptPasswordEncoder,
                                               StudentsService studentsService) {
        return args -> {

            String encodedPassword1 = bCryptPasswordEncoder.encode("123123");
            String encodedPassword2 = bCryptPasswordEncoder.encode("321123!");
            String encodedPassword3 = bCryptPasswordEncoder.encode("101010");
            Student Mehmet = new Student(
                    1L,
                    "Mehmet",
                    "mehmet.dundar@etiya.com",
                    LocalDate.of(2004, Month.JUNE, 27),
                    encodedPassword1,
                    Role.ADMIN,
                    Status.INACTIVE,
                    true
            );
            Student Halit = new Student(
                    2L,
                    "Halit",
                    "halit.bayraktar@etiya.com",
                    LocalDate.of(2016, Month.JUNE, 13),
                    encodedPassword2,
                    Role.STUDENT,
                    Status.INACTIVE,
                    true
            );
            Student Yahya = new Student(
                    3L,
                    "Yahya",
                    "yahya.diri@etiya.com",
                    LocalDate.of(1991, Month.JUNE, 1),
                    encodedPassword3,
                    Role.STUDENT,
                    Status.INACTIVE,
                    true
            );


            repository.saveAll(List.of(Mehmet, Halit, Yahya));
        };
    }
}
