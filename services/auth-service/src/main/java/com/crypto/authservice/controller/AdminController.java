package com.crypto.authservice.controller;

import com.crypto.authservice.entity.User;
import com.crypto.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AuthService authService;

    @GetMapping("/users")
    public List<User> getAllUsers() {

        return authService.getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(
            @PathVariable Long id
    ) {

        authService.deleteUser(id);

        return "User deleted successfully";
    }
}