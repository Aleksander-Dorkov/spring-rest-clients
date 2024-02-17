package com.example.feign.controller;

import com.example.feign.dto.ApplicationUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Api {

    @PostMapping("/users")
    public ResponseEntity<ApplicationUser> post(@RequestBody ApplicationUser applicationUser) {
        return ResponseEntity.status(200).body(applicationUser);
    }
}
