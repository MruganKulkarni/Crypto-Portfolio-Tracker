package com.crypto.authservice.service;

import com.crypto.authservice.dto.*;
import com.crypto.authservice.entity.RefreshToken;
import com.crypto.authservice.entity.Role;
import com.crypto.authservice.entity.User;
import com.crypto.authservice.repository.RefreshTokenRepository;
import com.crypto.authservice.repository.UserRepository;
import com.crypto.authservice.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final RefreshTokenRepository refreshTokenRepository;

    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new RuntimeException("Invalid email or password");
        }

        String accessToken = jwtService.generateToken(user);

        String refreshToken = jwtService.generateRefreshToken(user);
        refreshTokenRepository.findByUser(user)
                .ifPresent(existingToken ->
                        refreshTokenRepository.delete(existingToken)
                );

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .token(refreshToken)
                .user(user)
                .expiryDate(LocalDateTime.now().plusDays(7))
                .build();

        refreshTokenRepository.save(refreshTokenEntity);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    @Transactional
    public String logout(RefreshRequest request) {

        refreshTokenRepository.deleteByToken(
                request.getRefreshToken()
        );

        return "Logged out successfully";
    }

    public UserResponse updateProfile(UpdateProfileRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (request.getFullName() != null &&
                !request.getFullName().isBlank()) {

            user.setFullName(request.getFullName());
        }

        if (request.getPassword() != null &&
                !request.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );
        }

        User updatedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(updatedUser.getId())
                .fullName(updatedUser.getFullName())
                .email(updatedUser.getEmail())
                .role(updatedUser.getRole())
                .enabled(updatedUser.isEnabled())
                .createdAt(updatedUser.getCreatedAt())
                .build();
    }
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    public AuthResponse refreshToken(RefreshRequest request) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() ->
                        new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException("Refresh token expired");
        }

        User user = refreshToken.getUser();

        String newAccessToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken.getToken())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}