package com.miguelsperle.todolist.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(@NotBlank String email, @NotBlank String password) {}
