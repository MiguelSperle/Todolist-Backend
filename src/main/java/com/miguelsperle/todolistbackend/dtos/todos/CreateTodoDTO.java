package com.miguelsperle.todolistbackend.dtos.todos;

import jakarta.validation.constraints.NotBlank;

public record CreateTodoDTO(
        @NotBlank(message = "Title is required to create a todo")
        String title,

        @NotBlank(message = "Description is required to create a todo")
        String description
) {
}
