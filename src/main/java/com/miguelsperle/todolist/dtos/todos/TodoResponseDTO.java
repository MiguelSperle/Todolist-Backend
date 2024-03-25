package com.miguelsperle.todolist.dtos.todos;

import java.time.LocalDateTime;

public record TodoResponseDTO(String id, String title, String description, String userId, boolean completed, LocalDateTime created_at) {
}