package com.example.test.student;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
//Test des branches
@Entity
@Table
public @Data
class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private LocalDate anniv;
    private int age;

    public Student() {
    }

    public Student(Long id,
                   String name,
                   String email,
                   LocalDate anniv
                  ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.anniv = anniv;
    }

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
