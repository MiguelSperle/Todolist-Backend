package com.miguelsperle.todolist.repositories;

import com.miguelsperle.todolist.entities.TodosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<TodosEntity, String> {
    List<TodosEntity> findAllByUserId(String userId);
}
