package com.miguelsperle.todolistbackend.infra.security;

import com.miguelsperle.todolistbackend.repositories.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    private final UsersRepository usersRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if (token != null) {
            var email = tokenService.validateToken(token);
            Optional<UserDetails> user = this.usersRepository.findByEmail(email);
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.<java.util.Collection<? extends org.springframework.security.core.GrantedAuthority>>map(UserDetails::getAuthorities).orElse(null));
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
            if (cookie.getName().equals("token")) {
                return cookie.getValue();
            }
        }
        return null;

        // Método comentado abaixo equivale ao método padrão do Springf Security ( Tem que passar o token no headers de toda request de forma manual )

        // var authHeader = request.getHeader("Authorization");

        // if (authHeader == null) return null;

        // return authHeader.replace("Bearer ", "");
    }
}