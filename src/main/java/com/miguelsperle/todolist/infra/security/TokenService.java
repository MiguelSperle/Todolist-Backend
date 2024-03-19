package com.miguelsperle.todolist.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.miguelsperle.todolist.entities.UsersEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")

    private String secret; // O valor da secret vem das enviorements

    public String generateToken(UsersEntity user) { // Metódo para gerar os tokens
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("todolist-backend") // Nome do emissor, nesse caso o nome da aplicação
                    .withSubject(user.getUsername()) // usuário que ta recebendo esse login
                    .withExpiresAt(genExpirationDate()) // Tempo de expiração
                    .sign(algorithm); // Sign é para fazer a assinatura e a geração final

            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) { // Esse método vai me retornar o usuário que está registrado no token
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("todolist-backend") // Nome do emissor, nesse caso o nome da aplicação
                    .build()
                    .verify(token) // Descriptografei o token
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
