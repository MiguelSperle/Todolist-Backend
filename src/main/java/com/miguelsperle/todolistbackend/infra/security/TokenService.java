package com.miguelsperle.todolistbackend.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.algorithms.Algorithm;
import com.miguelsperle.todolistbackend.entities.users.UsersEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")

    private String secret; // O valor da secret vem das environments

    public String generateToken(UsersEntity user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("todolist-backend") // Emissor
                    .withSubject(user.getUsername()) // Email de quem está logando
                    .withExpiresAt(genExpirationDate()) // Tempo de expiração
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("todolist-backend")
                    .build()
                    .verify(token) // Descriptografa o token
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant genExpirationDate() {
        long expiration = 6 * 60 * 60 * 1000; // Equivale a 2 horas

        return LocalDateTime.now().plus(expiration, ChronoUnit.MILLIS).toInstant(ZoneOffset.of("-03:00"));
    }
}