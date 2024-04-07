package com.miguelsperle.todolistbackend.entities.todos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "todos")
@Entity(name = "todos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodosEntity {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private boolean completed;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @JoinColumn(name = "user_id", nullable = false) // Relacionamentos entre entidades
    private String userId;
}
