package com.miguelsperle.todolist.dtos.todos;

import jakarta.validation.constraints.NotBlank;

public record UpdateTodoDTO(
        String id,
        @NotBlank (message = "Para atualizar a tarefa deve ter um novo título") String title,
        @NotBlank (message = "Para atualizar a tarefa deve ter uma nova descrição") String description
) {
}