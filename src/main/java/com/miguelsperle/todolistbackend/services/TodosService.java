package com.miguelsperle.todolistbackend.services;

import com.miguelsperle.todolistbackend.dtos.todos.CreateTodoDTO;
import com.miguelsperle.todolistbackend.dtos.todos.TodoResponseDTO;
import com.miguelsperle.todolistbackend.dtos.todos.UpdateTodoDTO;
import com.miguelsperle.todolistbackend.entities.todos.TodosEntity;
import com.miguelsperle.todolistbackend.entities.todos.execptions.TaskUpdateDeniedExecption;
import com.miguelsperle.todolistbackend.entities.todos.execptions.TodoNotFoundExecption;
import com.miguelsperle.todolistbackend.entities.users.UsersEntity;
import com.miguelsperle.todolistbackend.repositories.TodosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TodosService {
    private final TodosRepository todosRepository;
    private final UsersService usersService;

    public void createTodo(CreateTodoDTO createTodoDTO){
        TodosEntity newTodo = new TodosEntity();

        newTodo.setTitle(createTodoDTO.title());
        newTodo.setDescription(createTodoDTO.description());
        newTodo.setCreatedAt(LocalDateTime.now());
        newTodo.setCompleted(false);
        newTodo.setUser(this.usersService.getUserAuthenticated().get());

        this.todosRepository.save(newTodo);
    }

    public void updateTodo(String id, UpdateTodoDTO updateTodoDTO){
        TodosEntity currentTodo = this.verificationUserIdAuthenticatedMatchesTodoOwnerId(id);

        currentTodo.setTitle(updateTodoDTO.newTitle());
        currentTodo.setDescription(updateTodoDTO.newDescription());

        this.todosRepository.save(currentTodo);
    }

    private TodosEntity getTodo(String id){
        return this.todosRepository.findById(id).orElseThrow(() -> new TodoNotFoundExecption("Todo not found"));
    }

    private TodosEntity verificationUserIdAuthenticatedMatchesTodoOwnerId(String id){
        TodosEntity currentTodo = this.getTodo(id);

        UsersEntity user = this.usersService.getUserAuthenticated().get();

        if(!Objects.equals(user.getId(), currentTodo.getUser().getId())) throw new TaskUpdateDeniedExecption("Task not allowed");

        return currentTodo;
    }

    public List<TodoResponseDTO> getAllTodos(){
        UsersEntity user = this.usersService.getUserAuthenticated().get();

        return this.todosRepository.findAllByUserId(user.getId()).stream().map(todoEntity -> new TodoResponseDTO(
                todoEntity.getId(),
                todoEntity.getTitle(),
                todoEntity.getDescription(),
                todoEntity.isCompleted(),
                todoEntity.getCreatedAt())).toList(); // Transform to a list again
    }

    public void deleteTodo(String id){
        TodosEntity currentTodo = this.verificationUserIdAuthenticatedMatchesTodoOwnerId(id);

        this.todosRepository.deleteById(id);
    }
}
