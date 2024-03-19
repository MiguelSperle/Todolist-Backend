package com.miguelsperle.todolist.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO
        (@NotBlank
                 (message = "Email é obrigatório")
         String email,
         @NotBlank(message = "Senha é obrigatória") String password) {
}
