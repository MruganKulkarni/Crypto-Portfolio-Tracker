package com.crypto.authservice.dto;

import com.crypto.authservice.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {

    private Long id;

    private String fullName;

    private String email;

    private Role role;

    private boolean enabled;

    private LocalDateTime createdAt;
}