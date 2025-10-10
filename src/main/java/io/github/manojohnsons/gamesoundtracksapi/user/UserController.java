package io.github.manojohnsons.gamesoundtracksapi.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.manojohnsons.gamesoundtracksapi.exception.GlobalExceptionHandler;
import io.github.manojohnsons.gamesoundtracksapi.user.dtos.UserRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.user.dtos.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Users")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @Operation(summary = "Add a new user", description = "Creates a new user in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided. See response body for details.", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Validation Error Example", summary = "Example response for a validation error on the 'username' field.", value = "[{\"field\": \"username\", \"message\": \"O nome do usuário não pode estar em branco.\"}]"))),
            @ApiResponse(responseCode = "409", description = "Username already exists in the system.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "Conflict Error Example", summary = "Example response for a conflict error in this route when attempting to create a user with a username that already exists in the system.", value = "{\"status\":\"CONFLICT\", \"message\":\"Username Fulana35 is already in use.\"}")))
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> insertUser(@RequestBody @Valid UserRequestDTO userRequestDTO,
            UriComponentsBuilder uriBuilder) {
        UserResponseDTO newUser = service.insertUser(userRequestDTO);
        URI location = uriBuilder.path("/users/{id}").buildAndExpand(newUser.getId()).toUri();

        return ResponseEntity.created(location).body(newUser);
    }

    @Operation(summary = "List all users", description = "Returns a list of all users registerd in the system.")
    @ApiResponse(responseCode = "200", description = "Successful operation. Returns a list of users, which may be empty if no users are found.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class))))
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> allUsers = service.getAllUsers();

        return ResponseEntity.ok(allUsers);
    }

    @Operation(summary = "Find a user by its ID", description = "Retrieves the details of a specific user by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "User Not Found Response Example", summary = "Example response when a user is not found the system.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"User not found with the specified ID: 99\"}")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(
            @Parameter(description = "Unique ID of the user.") @PathVariable Long id) {
        UserResponseDTO userFetched = service.getUserById(id);

        return ResponseEntity.ok(userFetched);
    }

    @Operation(summary = "Update an existing user", description = "Updates the information of an existing user by its ID. Users can only update their own account, unless they have ADMIN role. <br>"
            +
            "Note: If the username is updated, the current JWT will be invalidated, and the user must log in again to obtain a new token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided. See response body for details.", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Validation Error Example", summary = "Example response for a validation error on the 'username' field.", value = "[{\"field\": \"username\", \"message\": \"O nome do usuário não pode estar em branco.\"}]"))),
            @ApiResponse(responseCode = "401", description = "Unathorized - Access protected routes without credentials.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "Unathorized User Response Example", summary = "Example payload for a unathorized error.", value = "{\"status\":\"UNAUTHORIZED\", \"message\":\"Access unauthorized. You have to be authenticated to access this resource.\"}"))),
            @ApiResponse(responseCode = "403", description = "Forbidden - This can happen for two reasons: <br>"
                    +
                    "1) The provided JWT is invalid/expired (Authentication Failure). <br>" +
                    "2) The authenticated user is trying to update an account that is not theirs or does not have the required role (e.g., ADMIN) to perform this operation (Authorization Failure).", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                            @ExampleObject(name = "JWT Invalid Response Example", description = "Example payload for a forbidden error (Authentication Failure).", value = "{\"status\":\"FORBIDDEN\", \"message\":\"Invalid/expired JWT token!\"}"),
                            @ExampleObject(name = "Forbidden Error Response Example", description = "Example payload for a forbidden error (Authorization Failure).", value = "{\"status\":\"FORBIDDEN\", \"message\":\"Access Denied. You do not have permission to execute this action.\"}")
                    })),
            @ApiResponse(responseCode = "404", description = "User not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "User Not Found Response Example", summary = "Example response when a user is not found the system.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"User not found with the specified ID: 99\"}"))),
            @ApiResponse(responseCode = "409", description = "Username already exists in the system.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "Conflict Error Example", summary = "Example response for a conflict error in this route when attempting to update a user with a username that already exists in the system.", value = "{\"status\":\"CONFLICT\", \"message\":\"Username Fulana35 belongs to another user.\"}")))
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("principal.getId() == #id or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @Parameter(description = "Unique ID of the user.") @PathVariable Long id,
            @RequestBody @Valid UserRequestDTO userRequestDTO) {
        UserResponseDTO userToUpdate = service.updateUserById(id, userRequestDTO);

        return ResponseEntity.ok(userToUpdate);
    }

    @Operation(summary = "Delete a user", description = "Deletes a user from the system by its ID. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully."),
            @ApiResponse(responseCode = "401", description = "Unathorized - Access protected routes without credentials.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "Unathorized User Response Example", summary = "Example payload for a unathorized error.", value = "{\"status\":\"UNAUTHORIZED\", \"message\":\"Access unauthorized. You have to be authenticated to access this resource.\"}"))),
            @ApiResponse(responseCode = "403", description = "Forbidden - This can happen for two reasons: <br>"
                    +
                    "1) The provided JWT is invalid/expired (Authentication Failure). <br>" +
                    "2) The authenticated user does not have the required role (e.g., ADMIN) to perform this operation (Authorization Failure).", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                            @ExampleObject(name = "JWT Invalid Response Example", description = "Example payload for a forbidden error (Authentication Failure).", value = "{\"status\":\"FORBIDDEN\", \"message\":\"Invalid/expired JWT token!\"}"),
                            @ExampleObject(name = "Forbidden Error Response Example", description = "Example payload for a forbidden error (Authorization Failure).", value = "{\"status\":\"FORBIDDEN\", \"message\":\"Access Denied. You do not have permission to execute this action.\"}")
                    })),
            @ApiResponse(responseCode = "404", description = "User not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "User Not Found Response Example", summary = "Example response when a user is not found the system.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"User not found with the specified ID: 99\"}")))
    })
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(
            @Parameter(description = "Uniqye ID of the user.") @PathVariable Long id) {
        service.deleteUserById(id);
    }

    @Operation(summary = "Favorites a music.", description = "Adds a music to the user's list of favorites musics. The user must be authenticated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Music added to user favorites."),
            @ApiResponse(responseCode = "401", description = "Unathorized - Access protected routes without credentials.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "Unathorized User Response Example", summary = "Example payload for a unathorized error.", value = "{\"status\":\"UNAUTHORIZED\", \"message\":\"Access unauthorized. You have to be authenticated to access this resource.\"}"))),
            @ApiResponse(responseCode = "403", description = "Forbidden - The provided JWT is invalid/expired (Authentication Failure).", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "JWT Invalid Response Example", description = "Example payload for a forbidden error (Authentication Failure).", value = "{\"status\":\"FORBIDDEN\", \"message\":\"Invalid/expired JWT token!\"}"))),
            @ApiResponse(responseCode = "404", description = "Not Found - This can happen for two reasons: <br>"
                    +
                    "1) User not found with the specified ID. <br>" +
                    "2) Music not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                            @ExampleObject(name = "User Not Found Response Example", summary = "Example response when a user is not found in the system.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"User not found!\"}"),
                            @ExampleObject(name = "Music Not Found Response Example", summary = "Example response when a music is not found in the catalog.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Music not found with the specified ID: 99\"}")
                    }))
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/favorites/{musicId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void favoriteMusic(
            @Parameter(description = "Unique ID of the music.") @PathVariable Long musicId,
            Authentication authentication) {
        User userLogged = (User) authentication.getPrincipal();
        service.favoriteMusic(userLogged.getUsername(), musicId);
    }

    @Operation(summary = "Unfavorites a music", description = "Removes a music that was previously added to the user's list of favorites musics. The user must be authenticated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Music removed from the user's list of favorite musics"),
            @ApiResponse(responseCode = "401", description = "Unathorized - Access protected routes without credentials.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "Unathorized User Response Example", summary = "Example payload for a unathorized error.", value = "{\"status\":\"UNAUTHORIZED\", \"message\":\"Access unauthorized. You have to be authenticated to access this resource.\"}"))),
            @ApiResponse(responseCode = "403", description = "Forbidden - The provided JWT is invalid/expired (Authentication Failure).", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "JWT Invalid Response Example", description = "Example payload for a forbidden error (Authentication Failure).", value = "{\"status\":\"FORBIDDEN\", \"message\":\"Invalid/expired JWT token!\"}"))),
            @ApiResponse(responseCode = "404", description = "Not Found - This can happen for two reasons: <br>"
                    +
                    "1) User not found with the specified ID. <br>" +
                    "2) Music not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                            @ExampleObject(name = "User Not Found Response Example", summary = "Example response when a user is not found in the system.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"User not found!\"}"),
                            @ExampleObject(name = "Music Not Found Response Example", summary = "Example response when a music is not found in the catalog.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Music not found with the specified ID: 99\"}")
                    }))
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/favorites/{musicId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void unfavoriteMusic(
            @Parameter(description = "Unique ID of the music.") @PathVariable Long musicId,
            Authentication authentication) {
        User userLogged = (User) authentication.getPrincipal();
        service.unfavoriteMusic(userLogged.getUsername(), musicId);
    }
}
