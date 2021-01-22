package com.example.test.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }


    public Student getStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("L'étudiant " +studentId+ " n'existe pas"));
    }


    public void addStudent(Student student) {
        Optional<Student> studentExist = studentRepository.findStudentByEmail(student.getEmail());

        if(studentExist.isPresent())
            throw new IllegalStateException("Adresse email déjà utilisée");
        else
            studentRepository.save(student);
    }


    public void deleteById(Long id) {
        boolean studentExist = studentRepository.existsById(id);

        if(!studentExist)
            throw new IllegalStateException("Cet étudiant n'existe pas!");
        else
            studentRepository.deleteById(id);
    }


    @Transactional
    public void updateStudent(Long studentId,
                              String name,
                              String email)
    {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException("L'étudiant " + studentId + " n'existe pas"));


//        if (student.getName().equals(name)) throw new IllegalStateException("erreur");

        if(name != null &&
                name.length()>0 &&
                !student.getName().equals(name)) {
            student.setName(name);
        }

        if(email != null &&
                email.length()>0 &&
                !student.getEmail().equals(email)) {
            Optional<Student> studentEmail = studentRepository.findStudentByEmail(email);
            if(studentEmail.isPresent() ) {
                throw new IllegalStateException("Cet email est déjà utilisé");
            } else {
                student.setEmail(email);
            }

        }
    }

}
