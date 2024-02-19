package com.example.feign.controller;

import com.example.feign.dto.ApplicationUser;
import com.example.feign.dto.TestRecord2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Api {

    @PostMapping("/users")
    public ResponseEntity<ApplicationUser> post(@RequestBody ApplicationUser applicationUser) {
        return ResponseEntity.status(200).body(new ApplicationUser("response", 100));
    }

    @PostMapping("/users-test")
    public ResponseEntity<TestRecord2> post(@RequestBody Object obj) {
        System.out.println(obj);
        return ResponseEntity.ok().body(new TestRecord2("2222", "3333"));
    }
}
