package com.miguelsperle.todolistbackend.entities.users.execptions;

public class UserAlreadyExistsExecption extends RuntimeException {
    public UserAlreadyExistsExecption(String message){
        super(message);
    }
}
