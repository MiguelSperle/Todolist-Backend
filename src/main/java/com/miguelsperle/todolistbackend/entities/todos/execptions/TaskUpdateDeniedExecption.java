package com.miguelsperle.todolistbackend.entities.todos.execptions;

public class TaskUpdateDeniedExecption extends RuntimeException{
    public TaskUpdateDeniedExecption(String message){
        super(message);
    }
}
