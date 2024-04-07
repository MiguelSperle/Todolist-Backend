package com.miguelsperle.todolistbackend.dtos.todos;

import jakarta.validation.constraints.NotBlank;

public record UpdateTodoDTO(
        @NotBlank(message = "New title is required to update todo")
        String newTitle,

        @NotBlank(message = "New Description is required to update todo")
        String newDescription
) {
}
