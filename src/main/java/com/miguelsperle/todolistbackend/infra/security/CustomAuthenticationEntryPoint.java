package com.miguelsperle.todolistbackend.infra.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miguelsperle.todolistbackend.dtos.general.CustomAuthenticationEntryResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component("customAuthenticationEntryPoint")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        CustomAuthenticationEntryResponseDTO customAuthenticationEntryResponseDTO =
                new CustomAuthenticationEntryResponseDTO(HttpStatus.UNAUTHORIZED.value(), "Não autorizado");

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OutputStream responseStream = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, customAuthenticationEntryResponseDTO);
        responseStream.flush();
    }
}