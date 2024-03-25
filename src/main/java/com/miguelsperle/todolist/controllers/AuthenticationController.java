package com.miguelsperle.todolist.controllers;


import com.miguelsperle.todolist.dtos.auth.AuthenticationDTO;
import com.miguelsperle.todolist.dtos.auth.RegisterUserDTO;
import com.miguelsperle.todolist.dtos.user.UserResponseDTO;
import com.miguelsperle.todolist.entities.UsersEntity;
import com.miguelsperle.todolist.infra.security.TokenService;
import com.miguelsperle.todolist.response.ResponseHandler;
import com.miguelsperle.todolist.response.UserResponse;
import com.miguelsperle.todolist.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDTO authenticationDTO, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return ResponseHandler.generateResponse(String.valueOf(bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).findFirst().get()), HttpStatus.BAD_REQUEST);
        }

        var user = this.userService.findUserByEmail(authenticationDTO.email());


        if (user == null) {
            return ResponseHandler.generateResponse("Email e/ou senha invalidos.", HttpStatus.NOT_FOUND);
        }


        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationDTO.email(), authenticationDTO.password());

        var authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        var token = tokenService.generateToken((UsersEntity) authentication.getPrincipal());

        Cookie cookie = new Cookie("token", token);

        var cookiesExpiresInDays = 60 * 60 * 24 * 30;

        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(cookiesExpiresInDays);
        cookie.setPath("/");
        cookie.setAttribute("SameSite", "strict");

        response.addCookie(cookie);


        return ResponseHandler.generateResponse("Login realizado com sucesso.", HttpStatus.OK);
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

    @GetMapping("/user")
    public ResponseEntity<Object> user() {
        var user = this.userService.getUser(); // Pegando as info do usuário logado

        var userObject = UserResponse.generateResponse(new UserResponseDTO(user.getId(), user.getName(), user.getUsername(), user.getAvatar()));

        return ResponseHandler.generateResponse(userObject, HttpStatus.OK);
    }
}
