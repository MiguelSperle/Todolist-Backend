package com.miguelsperle.todolistbackend.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "Email is required to make login")
        String email,
        @NotBlank(message = "Password is required to make login")
        String password
)
{}
