package com.miguelsperle.todolistbackend.config;

import com.miguelsperle.todolistbackend.entities.todos.execptions.TaskUpdateDeniedExecption;
import com.miguelsperle.todolistbackend.entities.todos.execptions.TodoNotFoundExecption;
import com.miguelsperle.todolistbackend.entities.users.execptions.PasswordNotMatchExecption;
import com.miguelsperle.todolistbackend.entities.users.execptions.UserAlreadyExistsExecption;
import com.miguelsperle.todolistbackend.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UsernameNotFoundException exceptions){
        return ResponseHandler.generateResponse(exceptions.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsExecption.class)
    public ResponseEntity<Object> handleUserAlreadyExists(UserAlreadyExistsExecption exceptions){
        return ResponseHandler.generateResponse(exceptions.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordNotMatchExecption.class)
    public ResponseEntity<Object> handlePasswordNotMatch(PasswordNotMatchExecption exceptions){
        return ResponseHandler.generateResponse(exceptions.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TodoNotFoundExecption.class)
    public ResponseEntity<Object> handleTodoNotFound(TodoNotFoundExecption exceptions){
        return ResponseHandler.generateResponse(exceptions.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskUpdateDeniedExecption.class)
    public ResponseEntity<Object> handleTodoDenyUpdate(TaskUpdateDeniedExecption exceptions){
        return ResponseHandler.generateResponse(exceptions.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
