package com.example.chat.controller;

import com.example.chat.security.JwtTokenProvider;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/login")
    public String login(@RequestParam String username) {
        return jwtTokenProvider.generateToken(username);
    }
}
