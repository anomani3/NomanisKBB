package com.nomaniskabab.authservice.controller;

import com.nomaniskabab.authservice.model.User;
import com.nomaniskabab.authservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        String result = userService.register(user.getName(), user.getEmail(), user.getPassword());
        return ResponseEntity.ok(Map.of("message", result)); // message key only
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        String result = userService.login(user.getEmail(), user.getPassword());
        if (result.equals("Login Successful!")) {
            return ResponseEntity.ok(Map.of("message", result));
        }
        return ResponseEntity.status(401).body(Map.of("message", result));
    }
}
