package com.miguelsperle.todolistbackend.controllers;

import com.miguelsperle.todolistbackend.dtos.todos.CreateTodoDTO;
import com.miguelsperle.todolistbackend.dtos.todos.UpdateTodoDTO;
import com.miguelsperle.todolistbackend.response.ResponseHandler;
import com.miguelsperle.todolistbackend.services.TodosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodosController {
    private final TodosService todosService;

    @PostMapping("/create")
    public ResponseEntity<Object> createTodo(@RequestBody @Valid CreateTodoDTO createTodoDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseHandler.generateResponse(String.valueOf(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().get()), HttpStatus.BAD_REQUEST);
        }

        this.todosService.createTodo(createTodoDTO);

        return ResponseHandler.generateResponse("Todo created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateTodo(@PathVariable String id, @RequestBody @Valid UpdateTodoDTO updateTodoDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseHandler.generateResponse(String.valueOf(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().get()), HttpStatus.BAD_REQUEST);
        }

        this.todosService.updateTodo(id, updateTodoDTO);

        return ResponseHandler.generateResponse("Todo updated successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> allTodos(){
        return ResponseHandler.generateResponse(this.todosService.getAllTodos(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteTodo(@PathVariable String id){
        this.todosService.deleteTodo(id);

        return ResponseHandler.generateResponse("Todo deleted successfully", HttpStatus.OK);
    }
}
