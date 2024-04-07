package com.miguelsperle.todolistbackend.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserDTO(
        @NotBlank(message = "Email is required to make register on platform")
        String email,
        @NotBlank(message = "Name is required to make register on platform")
        String name,
        @NotBlank(message = "Password is required to make register on platform")
        String password
) {
}
