package io.github.manojohnsons.gamesoundtracksapi.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.manojohnsons.gamesoundtracksapi.exception.GlobalExceptionHandler;
import io.github.manojohnsons.gamesoundtracksapi.security.dtos.LoginRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.security.dtos.TokenJWTDTO;
import io.github.manojohnsons.gamesoundtracksapi.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Authentication")
@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private final AuthenticationManager manager;
    private final TokenService service;

    public AuthenticationController(AuthenticationManager manager, TokenService service) {
        this.manager = manager;
        this.service = service;
    }

    @Operation(summary = "Login into catalog", description = "Login a registered user into the catalog.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful. Returns a JWT token.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenJWTDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided. See response body for details.", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Validation Error Example", summary = "Example response for a validation error on the 'login' and 'password' fields.", value = "[{\"field\": \"login\", \"message\": \"não deve estar em branco.\"}, {\"field\": \"password\", \"message\": \"não deve estar em branco.\"}]"))),
            @ApiResponse(responseCode = "401", description = "Authentication failed. Invalid login or password.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "Authentication Failed Response Error", summary = "Example response for an authentication failed passing the wrong credentials.", value = "{\"status\":\"UNATHORIZED\", \"message\":\"Invalid credentials. Verify your username and password.\"}")))
    })
    @PostMapping
    public ResponseEntity<TokenJWTDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = service.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJWTDTO(tokenJWT));
    }

}
