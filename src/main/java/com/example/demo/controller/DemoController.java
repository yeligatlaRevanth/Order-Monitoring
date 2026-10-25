package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/admin/test")
    public ResponseEntity<String> adminTest(Authentication authentication) {
        return ResponseEntity.ok(
                "Welcome Admin: " + authentication.getName());
    }

    @GetMapping("/user/test")
    public ResponseEntity<String> userTest(Authentication authentication) {
        return ResponseEntity.ok(
                "Welcome User: " + authentication.getName());
    }
}