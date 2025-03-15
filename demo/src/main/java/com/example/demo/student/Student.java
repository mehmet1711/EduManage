package com.example.demo.student;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table

public class Student {



    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;



    private String password;
    private String name;
    private String email;
    private Role role;
    private  Status status;
    private boolean active;
    private LocalDate dob;
    @Transient
    private Integer age;
// spring security flutter angular react
    @ManyToMany
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )

    @JsonManagedReference
    private Set<Course> courses = new HashSet<>();



    public Student(Long id,
                   String name,
                   String email,
                   LocalDate dob,
                   String password,
                   Role role,
                   Status status,
                   boolean active
                   ){

        this.id=id;
        this.name=name;
        this.email=email;
        this.dob=dob;
        this.password=password;
        this.role=role;
        this.status=status;
        this.active=active;
    }
    public Student(String name,
                   String email,
                   LocalDate dob,
                   String password,
                   Role role,
                   Status status,
                   boolean active
    )
    {


        this.name=name;
        this.email=email;
        this.dob=dob;
        this.password=password;
        this.role=role;
        this.status=status;
        this.active=active;

    }

    public Student() {

    }
    public void addCourse(Course course) {
        this.courses.add(course);
        course.getStudents().add(this);
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
        course.getStudents().remove(this);
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getAge() {
        return Period.between(this.dob,LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getRoleName() {
        return role != null ? role.name() : null;
    }




    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", id=" + id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", dob=" + dob +
                '}';
    }
}
