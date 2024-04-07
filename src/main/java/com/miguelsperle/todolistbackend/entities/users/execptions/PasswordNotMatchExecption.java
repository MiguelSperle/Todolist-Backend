package com.miguelsperle.todolistbackend.entities.users.execptions;

public class PasswordNotMatchExecption extends RuntimeException{
    public PasswordNotMatchExecption(String message){
        super(message);
    }
}
