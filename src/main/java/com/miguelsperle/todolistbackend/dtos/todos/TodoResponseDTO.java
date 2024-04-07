package com.miguelsperle.todolistbackend.dtos.todos;

import java.time.LocalDateTime;

public record TodoResponseDTO(String id, String title, String description, boolean completed, LocalDateTime created_at) {
}
