package com.example.demo.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseDTO {

    private String name;
    private Set<StudentDTO> students = new HashSet<>();



    public CourseDTO(String name) {
        this.name = name;
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentDTO> students) {
        this.students = students;
    }
}
