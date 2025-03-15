package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentsService {


    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentDetailsService studentDetailsService;
    private  final CourseRepository courseRepository;

    @Autowired
    public StudentsService(StudentRepository studentRepository,
                           PasswordEncoder passwordEncoder,
                           StudentDetailsService studentDetailsService,
                           CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.studentDetailsService = studentDetailsService;
        this.courseRepository = courseRepository;
    }


    public void addCourseToStudent(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        if(student.getRole() == Role.ADMIN){
            throw new IllegalArgumentException("This id does not belong to a student.");
        }
        student.getCourses().add(course);
        course.getStudents().add(student);

        studentRepository.save(student);
        courseRepository.save(course);
    }



    public Optional<StudentDTO> getStudent() {
        String email = studentDetailsService.returnEmail();
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);


        return studentOptional.map(this::studentTostudentDTO);
    }
    // page dönecek  dto larda record kullan
    public Page<StudentDTO> getStudents(Pageable pageable) {
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return studentPage.map(this::studentTostudentDTO);
    }


    public void addNewStudent(StudentDTO studentDTO) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(studentDTO.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("This email has been taken");
        }
        Student student = studentDtoToStudent(studentDTO);


         String encodedPassword = passwordEncoder.encode(student.getPassword());
        student.setPassword(encodedPassword);
        studentRepository.save(student);
    }


    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("There is no student with id " + id)
        );

        student.setActive(false);

        studentRepository.save(student);

    }


        @Transactional
    public void updateStudent(Long studentID, String name, String email) {
        Student student = studentRepository.findById(studentID).orElseThrow(() -> new IllegalStateException("there is no student with this id "));

        Optional<Student> student2 = studentRepository.findStudentByEmail(studentDetailsService.returnEmail());

        if(student.getId() != student2.get().getId()) {
            throw new IllegalArgumentException("This ID does not belong to you");
        }
        else {
            if (name != null && name.length() > 0 && student.getName() != name) {
                student.setName(name);
            }
            if (email != null && email.length() > 0 && student.getEmail() != email) {
                Optional<Student> studentEmail = studentRepository.findStudentByEmail(email);
                if (studentEmail.isPresent()) {
                    throw new IllegalArgumentException("this email has been taken");
                }
                student.setEmail(email);
            }
        }
    }
    public Page<StudentDTO> getAllActiveUsers(Pageable pageable) {
        Page<Student> studentPage = studentRepository.findByStatus(Status.ACTIVE, pageable);
        return studentPage.map(this::studentTostudentDTO);
    }

    public Page<StudentDTO> getAllInactiveUsers(Pageable pageable) {
        Page<Student> studentPage = studentRepository.findByStatus(Status.INACTIVE, pageable);
        return studentPage.map(this::studentTostudentDTO);
    }
    public void setActive(String email) {
        Optional<Student> studentOpt = studentRepository.findStudentByEmail(email);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setStatus(Status.ACTIVE);
            studentRepository.save(student);
        }
    }

    public Page<StudentDTO> getActives(Pageable pageable) {
        Page<Student> studentPage = studentRepository.findByActive(true, pageable);
        return studentPage.map(this::studentTostudentDTO);
    }
    public List<Course> showCourses(){

      return courseRepository.findAll();
    }


    //modelmapper dto to student
    public StudentDTO studentTostudentDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setDob(student.getDob());
        studentDTO.setRole(student.getRole());
        studentDTO.setStatus(student.getStatus());
        studentDTO.setCourses(student.getCourses().stream()
                .map(course -> new CourseDTO(course.getName()))
                .collect(Collectors.toSet()));

        return studentDTO;
    }
    public Student studentDtoToStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setDob(studentDTO.getDob());
        student.setRole(studentDTO.getRole());
        student.setStatus(studentDTO.getStatus());

        student.setCourses(studentDTO.getCourses().stream()
                .map(courseDTO -> new Course(courseDTO.getName()))
                .collect(Collectors.toSet()));

        return student;
    }
    public CourseDTO courseToCourseDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName(course.getName());

        Set<StudentDTO> studentDTOs = course.getStudents().stream()
                .map(this::studentTostudentDTO)
                .collect(Collectors.toSet());

        courseDTO.setStudents(studentDTOs);
        return courseDTO;
    }

    public Course courseDtoToCourse(CourseDTO courseDto) {
        Course course = new Course();
        course.setName(courseDto.getName());

        Set<Student> students = courseDto.getStudents().stream()
                .map(this::studentDtoToStudent)
                .collect(Collectors.toSet());

        course.setStudents(students);
        return course;
    }

   public void addCourse(CourseDTO courseDto){
       Course course = courseDtoToCourse(courseDto) ;
       courseRepository.save(course);
   }

}

