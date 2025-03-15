package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StudentDetailsService implements UserDetailsService {

    //email of the current user
   private  String emailX;

    @Autowired
    private  StudentRepository studentRepository;


    public StudentDetailsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentDetailsService() {

    }
    public String returnEmail(){
        return emailX;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Student> studentOpt = studentRepository.findStudentByEmail(email);

        if (studentOpt.isEmpty()) {
            throw new UsernameNotFoundException("Invalid email.");
        }

        Student student = studentOpt.get();

        if (!student.isActive()) {
            throw new UsernameNotFoundException("This account is not active.");
        }

        emailX = email;
        return new StudentDetails(Optional.of(student));
    }

    public void setActiveStatus( ) {
        Student student = studentRepository.findStudentByEmail(emailX)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        student.setStatus(Status.ACTIVE);
        studentRepository.save(student);
    }
    public void setInactiveStatus(){
        Student student = studentRepository.findStudentByEmail(emailX)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        student.setStatus(Status.INACTIVE);
        studentRepository.save(student);
    }
    public List<String> getRoles(String email ) {
        Student student = studentRepository.findStudentByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Tek bir role sahip olduğunuz için bunu bir listeye koyuyoruz
        return Collections.singletonList(student.getRoleName());
    }

}
