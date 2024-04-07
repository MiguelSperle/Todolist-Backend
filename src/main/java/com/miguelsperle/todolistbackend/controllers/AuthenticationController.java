package com.miguelsperle.todolistbackend.controllers;

import com.miguelsperle.todolistbackend.dtos.auth.LoginDTO;
import com.miguelsperle.todolistbackend.dtos.auth.RegisterUserDTO;
import com.miguelsperle.todolistbackend.dtos.auth.UserResponseDTO;
import com.miguelsperle.todolistbackend.entities.users.UsersEntity;
import com.miguelsperle.todolistbackend.response.ResponseHandler;
import com.miguelsperle.todolistbackend.services.UsersService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDTO loginDTO, BindingResult bindingResult, HttpServletResponse response){
        if (bindingResult.hasErrors()) {
            return ResponseHandler.generateResponse(String.valueOf(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().get()), HttpStatus.BAD_REQUEST);
        }

        this.usersService.loginUser(loginDTO, response);

        return ResponseHandler.generateResponse("User has made login", HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid RegisterUserDTO registerUserDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseHandler.generateResponse(String.valueOf(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().get()), HttpStatus.BAD_REQUEST);
        }

        this.usersService.createUser(registerUserDTO);

        return ResponseHandler.generateResponse("User created", HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<Object> user(){
        UsersEntity user = this.usersService.getUserAuthenticated().get();

        return ResponseHandler.generateResponse(new UserResponseDTO(user.getName(), user.getEmail(), user.getAvatar()), HttpStatus.OK);
    }
}
