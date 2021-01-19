package com.example.test.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_ADMIN', 'ROLE_ADMINTEST')") //Gère les autorisations
    public List<Student> getStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping(path = "{studentId}")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT', 'ROLE_ADMIN', 'ROLE_ADMINTEST')")
    public Student getStudent(@PathVariable ("studentId") Long studentId) {
        return studentService.getStudent(studentId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('STUDENT_WRITE')")
    public void addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('STUDENT_WRITE')")
    public void deleteStudent(@PathVariable ("studentId") Long id) {
        studentService.deleteById(id);
    }

    @PutMapping(path = "{studentId}")
    @PreAuthorize("hasAuthority('STUDENT_WRITE')")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email)
    {
        studentService.updateStudent(studentId, name, email);
    }


}
