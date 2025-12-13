package com.nomaniskabab.authservice.controller;

import com.nomaniskabab.authservice.model.User;
import com.nomaniskabab.authservice.repository.UserRepository;
import com.nomaniskabab.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserService userService;


    @Autowired
    private UserRepository userRepository;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        String result = userService.register(user.getName(), user.getEmail(), user.getPassword());
        return ResponseEntity.ok(Map.of("message", result)); // message key only
    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User request) {

        return userRepository.findByEmail(request.getEmail())
                .map(user -> {
                    if (user.getPassword().equals(request.getPassword())) {

                        Map<String, String> response = new HashMap<>();
                        response.put("message", "Login Successful!");
                        response.put("name", user.getName());  // <-- RETURN THE NAME

                        return ResponseEntity.ok(response);
                    }
                    return ResponseEntity.status(401).body(Map.of("message", "Wrong Password!"));
                })
                .orElse(ResponseEntity.status(404).body(Map.of("message", "User not found!")));
    }

}
