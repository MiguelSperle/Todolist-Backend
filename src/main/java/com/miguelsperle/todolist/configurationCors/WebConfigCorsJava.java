package com.miguelsperle.todolist.configurationCors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfigCorsJava implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Apenas tal link vai poder chamar nossa API
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Permitir apenas determinados métodos HTTP
                .allowedHeaders("*") // Permitir todos os cabeçalhos
                .allowCredentials(true); // Permitir passar cookies pelo credentials
    }
}
