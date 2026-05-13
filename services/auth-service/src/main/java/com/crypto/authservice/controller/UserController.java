package com.crypto.authservice.controller;

import com.crypto.authservice.dto.UpdateProfileRequest;
import com.crypto.authservice.entity.User;
import com.crypto.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.crypto.authservice.dto.UserResponse;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @GetMapping("/me")
    public String currentUser(Authentication authentication) {

        return "Logged in user: " + authentication.getName();
    }

    @PutMapping("/me")
    public UserResponse updateProfile(
            @RequestBody UpdateProfileRequest request
    ) {
        return authService.updateProfile(request);
    }
}