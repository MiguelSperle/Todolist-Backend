package com.miguelsperle.todolist.entities;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name = "todos")
@Entity(name = "todos")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class TodosEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID)

    private String id;
    private String title;
    private String description;
    private boolean completed;

    @Column(name = "user_id")
    private String userId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public TodosEntity(String title, String description, String userId, boolean completed){
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.completed = completed;
    }
}
