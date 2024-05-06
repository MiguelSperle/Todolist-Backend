package com.miguelsperle.todolistbackend.dtos.todos;

import jakarta.validation.constraints.NotNull;

public record UpdateTaskCompletionStatusDTO(
        @NotNull(message = "Completion status must be provided.")
        Boolean completed
) {
}
