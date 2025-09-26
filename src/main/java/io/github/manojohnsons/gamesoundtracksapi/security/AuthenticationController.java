package io.github.manojohnsons.gamesoundtracksapi.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.manojohnsons.gamesoundtracksapi.security.dtos.LoginRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.security.dtos.TokenJWTDTO;
import io.github.manojohnsons.gamesoundtracksapi.user.User;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/login")
public class AuthenticationController {
    
    private final AuthenticationManager manager;
    private final TokenService service;

    public AuthenticationController(AuthenticationManager manager, TokenService service) {
        this.manager = manager;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TokenJWTDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = service.generateToken((User) authentication.getPrincipal());
        
        return ResponseEntity.ok(new TokenJWTDTO(tokenJWT));
    }
    
}
