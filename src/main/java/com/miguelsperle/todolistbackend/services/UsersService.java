package com.miguelsperle.todolistbackend.services;

import com.miguelsperle.todolistbackend.dtos.auth.LoginDTO;
import com.miguelsperle.todolistbackend.dtos.auth.RegisterUserDTO;
import com.miguelsperle.todolistbackend.entities.users.UsersEntity;
import com.miguelsperle.todolistbackend.entities.users.execptions.PasswordNotMatchExecption;
import com.miguelsperle.todolistbackend.entities.users.execptions.UserAlreadyExistsExecption;
import com.miguelsperle.todolistbackend.infra.security.TokenService;
import com.miguelsperle.todolistbackend.repositories.UsersRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public Optional<UsersEntity> getUserAuthenticated() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return (Optional<UsersEntity>) userDetails;
    }

    public Optional<UsersEntity> getUserByEmail(String email){
        return this.usersRepository.findByEmail(email);
    }

    public void createUser(RegisterUserDTO registerUserDTO) { // Method create user
        UsersEntity newUser = new UsersEntity();

        this.verificationUserAlreadyExistsByEmail(registerUserDTO.email());

        final String AVATAR_URL_IMAGE = "https://res.cloudinary.com/dnsxuxnto/image/upload/v1691878181/bm6z0rap3mkstebtopol.png";

        newUser.setEmail(registerUserDTO.email());
        newUser.setName(registerUserDTO.name());
        newUser.setPassword(this.passwordEncoder.encode(registerUserDTO.password()));
        newUser.setAvatar(AVATAR_URL_IMAGE);
        newUser.setCreatedAt(LocalDateTime.now());

        this.usersRepository.save(newUser);
    }

    private void verificationUserAlreadyExistsByEmail(String email){
        Optional<UsersEntity> user = this.getUserByEmail(email);

        if(user.isPresent()) throw new UserAlreadyExistsExecption("User already exists");
    }

    public void loginUser(LoginDTO loginDTO, HttpServletResponse response){ // Method login user
        this.verificationNotExistsUserByEmail(loginDTO.email());

        this.verificationPasswordMatch(loginDTO.password(), loginDTO.email());

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.email(),loginDTO.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        String token = this.tokenService.generateToken((UsersEntity) auth.getPrincipal());

        Cookie cookie = new Cookie("token", token);

        int cookiesExpiresInDays = 60 * 60 * 24 * 30;

        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(cookiesExpiresInDays);
        cookie.setPath("/");
        cookie.setAttribute("SameSite", "strict");

        response.addCookie(cookie);
    }

    private void verificationNotExistsUserByEmail(String email){
        Optional<UsersEntity> user = this.getUserByEmail(email);

        if(user.isEmpty()) throw new UsernameNotFoundException("Email and/or Password are wrong");
    }

    private void verificationPasswordMatch(String currentPassword, String email){
        Optional<UsersEntity> user = this.getUserByEmail(email);

        boolean verificationPassword = this.passwordEncoder.matches(currentPassword, user.get().getPassword());

        if(!verificationPassword) throw new PasswordNotMatchExecption("Email and/or Password are wrong");
    }
}
