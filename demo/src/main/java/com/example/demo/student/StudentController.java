package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1")

public class StudentController {
    private final StudentsService studentsService;

    @Autowired
    public StudentController(StudentsService studentsService) {
        this.studentsService = studentsService;
    }
    @GetMapping(path="/student")
    public Optional<StudentDTO> getStudent(){
        return studentsService.getStudent();
    }

    @GetMapping(path = "/admin")
    public Optional<StudentDTO> AdminData( ) {
        return studentsService.getStudent();
    }
    @GetMapping(path = "/admin/all")
    public Page<StudentDTO> getAllStudents(Pageable pageable){
        return studentsService.getStudents(pageable);
    }


    @PostMapping(path = "/admin/register")
    public void register(@RequestBody StudentDTO studentDto){
        studentsService.addNewStudent(studentDto);
    }
    @DeleteMapping(path = "/admin/{studentID}")
    public void deleteStudent(@PathVariable("studentID") Long studentID){
        studentsService.deleteStudent(studentID);

    }
    @PutMapping(path = "/student/{studentID}")
    public void  updateStudent(@PathVariable("studentID") Long studentID,
                @RequestParam(required = false) String name,
                @RequestParam(required = false) String email){
        studentsService.updateStudent(studentID,name,email);

    }
    @GetMapping("/admin/onlineUsers")
    public Page<StudentDTO> getActiveStudents(Pageable pageable ) {
        return  studentsService.getAllActiveUsers(pageable);
    }
    @GetMapping(path = "/admin/show")
    public List<Course> showCourse(){
        return studentsService.showCourses();
    }

    @GetMapping("/admin/offlineUsers")
    public Page<StudentDTO> getInactiveStudents(Pageable pageable ) {
        return studentsService.getAllInactiveUsers(pageable);
    }
    @GetMapping(path = "/admin/actives")
    public Page<StudentDTO> getActiveUsers(Pageable pageable){

        return studentsService.getActives(pageable);

    }
    @PostMapping("/admin/{studentId}/courses/{courseId}")
    public ResponseEntity<?> addCourseToStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        studentsService.addCourseToStudent(studentId, courseId);
        return ResponseEntity.ok("Course added to student successfully.");
    }
    @PostMapping(value = "/admin/addCourse", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addCourse(@RequestBody CourseDTO courseDto) {

       studentsService.addCourse(courseDto);
    }


}
