package io.github.manojohnsons.gamesoundtracksapi.doc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.manojohnsons.gamesoundtracksapi.exception.GlobalExceptionHandler;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unathorized - Access protected routes without credentials.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "Unathorized User Response Example", summary = "Example payload for a unathorized error.", value = "{\"status\":\"UNAUTHORIZED\", \"message\":\"Access unauthorized. You have to be authenticated to access this resource.\"}"))),
        @ApiResponse(responseCode = "403", description = "Forbidden - This can happen for two reasons: <br>" +
                "1) The provided JWT is invalid/expired (Authentication Failure). <br>" +
                "2) The authenticated user does not have the required role (e.g., ADMIN) to access this resource (Authorization Failure).", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                        @ExampleObject(name = "JWT Invalid Response Example", description = "Example payload for a forbidden error (Authentication Failure).", value = "{\"status\":\"FORBIDDEN\", \"message\":\"Invalid/expired JWT token!\"}"),
                        @ExampleObject(name = "Forbidden Role Response Example", description = "Example payload for a forbidden error (Authorization Failure).", value = "{\"status\":\"FORBIDDEN\", \"message\":\"Access denied. You do not have permission to access this resource.\"}")
                }))
})
public @interface ApiAuthErrorResponses {

}
