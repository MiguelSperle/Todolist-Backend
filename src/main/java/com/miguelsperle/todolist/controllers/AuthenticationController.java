package com.miguelsperle.todolist.controllers;

import com.miguelsperle.todolist.dtos.auth.AuthenticationDTO;
import com.miguelsperle.todolist.dtos.user.RegisterUserDTO;
import com.miguelsperle.todolist.entities.UsersEntity;
import com.miguelsperle.todolist.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired // Injentando a dependencia
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO authenticationDTO){
        System.out.println(authenticationDTO);

        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.password());

        var authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterUserDTO registerUserDTO){
        System.out.println(registerUserDTO);

        if(this.userService.findUserByEmail(registerUserDTO.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerUserDTO.password());

        var user = new UsersEntity(registerUserDTO.email(), registerUserDTO.name(), encryptedPassword);

        this.userService.saveUser(user);

        return ResponseEntity.ok().build();
    }
}
