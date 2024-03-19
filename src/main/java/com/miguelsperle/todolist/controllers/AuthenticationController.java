package com.miguelsperle.todolist.controllers;

import com.auth0.jwt.JWT;
import com.miguelsperle.todolist.dtos.auth.AuthenticationDTO;
import com.miguelsperle.todolist.dtos.auth.LoginResponseDTO;
import com.miguelsperle.todolist.dtos.user.RegisterUserDTO;
import com.miguelsperle.todolist.entities.UsersEntity;
import com.miguelsperle.todolist.infra.security.TokenService;
import com.miguelsperle.todolist.response.ResponseHandler;
import com.miguelsperle.todolist.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired // Injentando a dependencia
    private AuthenticationManager authenticationManager;

    @Autowired // Injentando a dependencia
    private UserService userService;

    @Autowired // Injentando a dependencia
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authenticationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseHandler.generateResponse(String.valueOf(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().get()), HttpStatus.BAD_REQUEST);
        }

        var user = this.userService.findUserByEmail(authenticationDTO.email());


        if (user == null) {
            return ResponseHandler.generateResponse("Email e/ou senha invalidos.", HttpStatus.NOT_FOUND);
        }

        try {
            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.password());

            var authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            var token = tokenService.generateToken((UsersEntity) authentication.getPrincipal());

            return ResponseHandler.generateResponse("Login realizado com sucesso.", HttpStatus.OK, new LoginResponseDTO(token));
        } catch (BadCredentialsException exception) {
            return ResponseHandler.generateResponse("Email e/ou senha invalidos.", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterUserDTO registerUserDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseHandler.generateResponse(String.valueOf(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().get()), HttpStatus.BAD_REQUEST);
        }

        if (this.userService.findUserByEmail(registerUserDTO.email()) != null)
            return ResponseHandler.generateResponse("Usuário já cadastrado!", HttpStatus.BAD_REQUEST);

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerUserDTO.password());
        String avatar = "https://res.cloudinary.com/dnsxuxnto/image/upload/v1691878181/bm6z0rap3mkstebtopol.png";

        var user = new UsersEntity(registerUserDTO.email(), registerUserDTO.name(), avatar, encryptedPassword);

        this.userService.saveUser(user);

        return ResponseHandler.generateResponse("Sua conta foi criada.", HttpStatus.CREATED);
    }

    @GetMapping("/user") // TESTE
    public ResponseEntity<Object> user(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");

        var token = authHeader.replace("Bearer ", "");

        var email = JWT.decode(token).getSubject();

        var user = this.userService.findUserByEmail(email);
        System.out.println(user.getName());

        return ResponseEntity.ok(user);
    }
}
