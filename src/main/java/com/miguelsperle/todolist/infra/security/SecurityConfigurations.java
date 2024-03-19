package com.miguelsperle.todolist.infra.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Indicando ao spring que é uma classe de configuração
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired // Injentando a dependencia
    SecurityFilter securityFilter;

    // SecurityFilterChain vai fazer validações ao usuarios, para ver se ele é apto a fazer as requisições
    @Bean // Injenção correta do método
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() // Rotas que todos vão poder acessar
                                .requestMatchers(HttpMethod.POST, "/auth/register").permitAll() // Rotas que todos vão poder acessar
                                .anyRequest().authenticated()) // Qualquer rota diferentes das que tão com permiteAll, precisa que o user esteja autenticado
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean // Injenção correta do método
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean // Injenção correta do método
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
