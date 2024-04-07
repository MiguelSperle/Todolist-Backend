package com.miguelsperle.todolistbackend.entities.todos.execptions;

public class TodoNotFoundExecption extends RuntimeException{
    public TodoNotFoundExecption(String message){
        super(message);
    }
}
