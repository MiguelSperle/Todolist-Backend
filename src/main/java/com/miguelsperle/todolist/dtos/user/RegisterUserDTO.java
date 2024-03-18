package com.miguelsperle.todolist.dtos.user;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserDTO(@NotBlank String email, @NotBlank String name,  @NotBlank String password) {}
