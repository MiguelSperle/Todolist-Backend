package com.miguelsperle.todolist.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserDTO(@NotBlank(message = "Email é obrigatório") String email,
                              @NotBlank(message = "Nome é obrigatório") String name,
                              @NotBlank(message = "Senha é obrigatória") String password) {
}
