package com.example.test.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public @Data
class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private LocalDate anniv;
    private int age;


    public Student(String name,
                   String email,
                   LocalDate anniv
                   ) {
        this.name = name;
        this.email = email;
        this.anniv = anniv;
    }

    public int getAge() {
        return Period.between(anniv, LocalDate.now()).getYears();
    }
}
