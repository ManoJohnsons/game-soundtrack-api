package io.github.manojohnsons.gamesoundtracksapi.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.manojohnsons.gamesoundtracksapi.exception.InvalidTokenException;
import io.github.manojohnsons.gamesoundtracksapi.exception.GlobalExceptionHandler.ApiError;
import io.github.manojohnsons.gamesoundtracksapi.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = retrieveToken(request);

        try {
            if (tokenJWT != null) {
                var subject = tokenService.getSubject(tokenJWT);
                var user = userRepository.findByUsername(subject);

                if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        } catch (InvalidTokenException e) {
            ApiError error = new ApiError(HttpStatus.FORBIDDEN, e.getMessage());

            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");

            response.getWriter().write(new ObjectMapper().writeValueAsString(error));
        }
    }

    private String retrieveToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }

}
