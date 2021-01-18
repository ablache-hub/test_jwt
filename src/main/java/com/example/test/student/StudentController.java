package com.example.test.student;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    @Autowired
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping(path = "{studentId}")
    public Student getStudent(
            @PathVariable ("studentId") Long studentId) {
        return studentService.getStudent(studentId);
    }

    @PostMapping
    public void addStudent(
            @RequestBody Student student) {
        studentService.addStudent(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(
            @PathVariable ("studentId") Long id) {
        studentService.deleteById(id);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email)
    {
        studentService.updateStudent(studentId, name, email);
    }


}
