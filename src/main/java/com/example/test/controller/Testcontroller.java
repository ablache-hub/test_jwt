package com.example.test.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/test")
@AllArgsConstructor
public class Testcontroller {


        @GetMapping
        @PreAuthorize("hasRole('ROLE_USER')")
        public String testToken() {
            return "test";
        }
}
