package com.miguelsperle.todolist.services;

import com.miguelsperle.todolist.dtos.todos.TodoResponseDTO;
import com.miguelsperle.todolist.dtos.todos.UpdateTodoDTO;
import com.miguelsperle.todolist.entities.TodosEntity;
import com.miguelsperle.todolist.repositories.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired // Injetando a dependencia
    private TodoRepository todoRepository;

    public List<TodoResponseDTO> getAllTodos(String userId) {
        return this.todoRepository.findAllByUserId(userId).stream().map(todoEntity ->
                new TodoResponseDTO(todoEntity.getId(),
                        todoEntity.getTitle(),
                        todoEntity.getDescription(),
                        todoEntity.getUserId(),
                        todoEntity.isCompleted(),
                        todoEntity.getCreatedAt())).toList(); // Retoma uma lista
    }

    public Optional<TodoResponseDTO> getTodo(String id) {
        var todo = this.todoRepository.findById(id);
        return todo.map((todoEntity) -> new TodoResponseDTO(todoEntity.getId(),
                todoEntity.getTitle(),
                todoEntity.getDescription(),
                todoEntity.getUserId(),
                todoEntity.isCompleted(),
                todoEntity.getCreatedAt()));
    }

    public TodosEntity updateTodo(UpdateTodoDTO updateTodoDTO) {
        var updateTodo = this.todoRepository.getReferenceById(updateTodoDTO.id());
        updateTodo.setTitle(updateTodoDTO.title());
        updateTodo.setDescription(updateTodoDTO.description());

        return this.todoRepository.save(updateTodo);
    }


    public TodosEntity saveTodo(TodosEntity todosEntity) {
       return this.todoRepository.save(todosEntity);
    }
}
