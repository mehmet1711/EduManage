package com.example.demo.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findByStatus(Status status, Pageable pageable);

    Page<Student> findByActive(boolean active, Pageable pageable);

    Optional<Student> findStudentByEmail(String email);

    Page<Student> findAll(Pageable pageable);

}
