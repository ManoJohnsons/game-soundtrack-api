package io.github.manojohnsons.gamesoundtracksapi.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.manojohnsons.gamesoundtracksapi.exception.GlobalExceptionHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        GlobalExceptionHandler.ApiError error = new GlobalExceptionHandler.ApiError(HttpStatus.FORBIDDEN,
                "Acesso negado, você não tem permissão para acessar esse recurso.");

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");

        response.getWriter().write(new ObjectMapper().writeValueAsString(error));
    }

}
