package com.miguelsperle.todolistbackend.repositories;

import com.miguelsperle.todolistbackend.entities.todos.TodosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodosRepository extends JpaRepository<TodosEntity, String> {
    List<TodosEntity> findAllByUserId(String userId);
}
