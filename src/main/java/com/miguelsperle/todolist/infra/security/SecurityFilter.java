package com.miguelsperle.todolist.infra.security;

import com.miguelsperle.todolist.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired // Injentando a dependencia
    private TokenService tokenService;

    @Autowired // Injentando a dependencia
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request); // Pegando o token que vem do método recoverToken

        if (token != null) {
            var email = tokenService.validateToken(token);
            UserDetails user = this.userRepository.findByEmail(email);
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        // Método abaixo equivale ao passar e pegar o token a partir do withCredentials ( Passa o token no headers de forma automatica )

        if (request.getCookies() == null) {
            return null;
        }

        for (Cookie cookie : request.getCookies()) {
            // Variável cookie vai representa em cada momento do loop um elemento do array request.getCookies()
            if ("token".equals(cookie.getName())) {
                return cookie.getValue(); // Retorno o valor do cookie ( token )
            }
        }

        return null;

        // Método comentado abaixo equivale ao método padrão do Springf Security ( Tem que passar o token no headers de toda request de forma manual )

        // var authHeader = request.getHeader("Authorization");

        // if (authHeader == null) return null;

        // return authHeader.replace("Bearer ", "");
    }
}
