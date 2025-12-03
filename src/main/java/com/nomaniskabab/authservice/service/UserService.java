package com.nomaniskabab.authservice.service;

import com.nomaniskabab.authservice.model.User;
import com.nomaniskabab.authservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(String name, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            return "Email already exists!";
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);  // PLAIN PASSWORD (as you requested)

        userRepository.save(user);
        return "User registered successfully!";
    }

    public String login(String email, String password) {
        return userRepository.findByEmail(email)
                .map(user -> user.getPassword().equals(password)
                        ? "Login Successful!"
                        : "Wrong Password!")
                .orElse("User not found!");
    }
}
