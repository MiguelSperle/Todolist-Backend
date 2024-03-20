package com.miguelsperle.todolist.dtos.todos;

import jakarta.validation.constraints.NotBlank;

public record CreateTodoDTO(@NotBlank(message = "Para criar uma atividade é necessário criar um título") String title,
                            @NotBlank(message = "Para criar uma atividade é necessário passar algo na descrição") String description) {
}
