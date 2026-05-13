package com.crypto.authservice.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {

    private String fullName;

    private String password;
}