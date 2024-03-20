package com.miguelsperle.todolist.services;

import com.miguelsperle.todolist.entities.TodosEntity;
import com.miguelsperle.todolist.entities.UsersEntity;
import com.miguelsperle.todolist.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired // Injetando a dependencia
    private TodoRepository todoRepository;

    public List<TodosEntity> getAllTodos(String userId){
        return this.todoRepository.getAllByUserId(userId);
    }

    public TodosEntity saveTodo(TodosEntity todosEntity){
        return this.todoRepository.save(todosEntity);
    }


}
