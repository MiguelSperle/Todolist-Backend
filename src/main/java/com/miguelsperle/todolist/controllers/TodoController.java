package com.miguelsperle.todolist.controllers;

import com.miguelsperle.todolist.dtos.todos.CreateTodoDTO;
import com.miguelsperle.todolist.dtos.todos.UpdateTodoDTO;
import com.miguelsperle.todolist.entities.TodosEntity;
import com.miguelsperle.todolist.repositories.TodoRepository;
import com.miguelsperle.todolist.repositories.UserRepository;
import com.miguelsperle.todolist.response.ResponseHandler;
import com.miguelsperle.todolist.services.TodoService;
import com.miguelsperle.todolist.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public ResponseEntity<Object> createTodo(@RequestBody @Valid CreateTodoDTO createTodoDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseHandler.generateResponse(String.valueOf(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().get()), HttpStatus.BAD_REQUEST);
        }

        var userId = this.userService.getUser().getId(); // Pegando id do usuario logado

        var todo = new TodosEntity(createTodoDTO.title(), createTodoDTO.description(), userId, false);

        this.todoService.saveTodo(todo);

        return ResponseHandler.generateResponse("Sua atividade foi cadastrada", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getAllTodos(){
        var userId = this.userService.getUser().getId();

        return ResponseHandler.generateResponse(this.todoService.getAllTodos(userId), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateTodo(@PathVariable String id, @RequestBody @Valid UpdateTodoDTO updateTodoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseHandler.generateResponse(String.valueOf(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().get()), HttpStatus.BAD_REQUEST);
        }

        var userId = this.userService.getUser().getId();

        var verificationExistingTodo = this.todoService.getTodo(id);


        if(verificationExistingTodo.isEmpty()){
            return ResponseHandler.generateResponse("Tarefa não encontrada", HttpStatus.NOT_FOUND);
        }

        if (!Objects.equals(userId, verificationExistingTodo.get().userId())) {
            return ResponseHandler.generateResponse("Tarefa não permitida", HttpStatus.BAD_REQUEST);
        }

        this.todoService.updateTodo(new UpdateTodoDTO(id, updateTodoDTO.title(), updateTodoDTO.description()));

        return ResponseHandler.generateResponse("Tarefa atualizada com sucesso", HttpStatus.OK);
    }
}
