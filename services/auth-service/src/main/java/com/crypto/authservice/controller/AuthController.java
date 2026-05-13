package com.crypto.authservice.controller;

import com.crypto.authservice.dto.AuthResponse;
import com.crypto.authservice.dto.LoginRequest;
import com.crypto.authservice.dto.RegisterRequest;
import com.crypto.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.crypto.authservice.dto.AuthResponse;
import com.crypto.authservice.dto.RefreshRequest;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
    @GetMapping("/profile")
    public String profile() {
        return "Protected profile endpoint accessed";
    }
    @PostMapping("/refresh")
    public AuthResponse refreshToken(
            @RequestBody RefreshRequest request
    ) {
        return authService.refreshToken(request);
    }
    @PostMapping("/logout")
    public String logout(
            @RequestBody RefreshRequest request
    ) {
        return authService.logout(request);
    }

}