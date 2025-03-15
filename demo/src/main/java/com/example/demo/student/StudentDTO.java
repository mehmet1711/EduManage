package com.example.demo.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data

@JsonIgnoreProperties(ignoreUnknown = true)

public class StudentDTO {
    private String name ;
    private  String email;
    private LocalDate dob;
    private Role role;
    private Status status;
    private Set<CourseDTO> courses;



    public Set<CourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseDTO> coursesDto) {
        this.courses = coursesDto;
    }





    public StudentDTO(String email, String password, Role role) {
        this.email=email;

        this.role=role;

    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
//    public Student studentDTOToStudent (StudentDTO studentDTO){
//
//        return Student.builder()
//                .name(studentDTO.getName())
//                .email(studentDTO.getEmail())
//                .dob(studentDTO.getDob())
//                .password(studentDTO.getPassword())
//                .build();
//    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }




}
