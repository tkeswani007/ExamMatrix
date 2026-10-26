package com.exammatrix.controller;

import com.exammatrix.model.User;
import com.exammatrix.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping({"/register", "/signup"})
    public Map<String, Object> register(@RequestBody User user) {
        // Map email to username if username is null
        if (user.getUsername() == null && user.getName() != null) {
            user.setUsername(user.getName());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return Map.of("success", true, "message", "User registered successfully!");
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User loginRequest) {
        // Handle both email/username fields from frontend
        String queryUsername = loginRequest.getUsername() != null ? loginRequest.getUsername() : "";
        User user = userRepository.findByUsername(queryUsername);
        
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return Map.of("success", true, "message", "Login successful!");
        }
        return Map.of("success", false, "message", "Invalid username or password.");
    }
}