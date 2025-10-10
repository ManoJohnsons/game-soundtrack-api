package io.github.manojohnsons.gamesoundtracksapi.game;

import java.net.URI;
import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.manojohnsons.gamesoundtracksapi.doc.ApiAuthErrorResponses;
import io.github.manojohnsons.gamesoundtracksapi.exception.GlobalExceptionHandler;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameFilterDTO;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameResponseDTO;
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

@Tag(name = "Games")
@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService service;

    @Operation(summary = "Add a new game", description = "Creates a new game in the catalog. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Game created successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GameResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided. See response body for details.", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Validation Error Example", summary = "Example response for a validation error on the 'gameTitle' field.", value = "[{\"field\": \"gameTitle\", \"message\": \"O nome do jogo não pode estar em branco.\"}]")))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<GameResponseDTO> insertGame(@RequestBody @Valid GameRequestDTO gameRequestDTO,
            UriComponentsBuilder uriBuilder) {
        GameResponseDTO newGame = service.insertGame(gameRequestDTO);
        URI location = uriBuilder.path("/games/{id}").buildAndExpand(newGame.getId()).toUri();

        return ResponseEntity.created(location).body(newGame);
    }

    @Operation(summary = "List all games", description = "Returns a list of all games in the catalog. The list can be filtered by game title (case-insensitive, partial match).")
    @ApiResponse(responseCode = "200", description = "Successful operation. Returns a list of games, which may be empty if no games are found or match the criteria.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GameResponseDTO.class))))
    @GetMapping
    public ResponseEntity<List<GameResponseDTO>> searchAllGames(@ParameterObject GameFilterDTO filterDTO) {
        List<GameResponseDTO> games = service.searchAllGames(filterDTO);

        return ResponseEntity.ok(games);
    }

    @Operation(summary = "Find a game by its ID", description = "Retrieves the details of a specific game by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game found successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GameResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Game not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<GameResponseDTO> searchGameById(
            @Parameter(description = "Unique ID of the game.") @PathVariable Long id) {
        var gameFetched = service.searchGameById(id);

        return ResponseEntity.ok(gameFetched);
    }

    @Operation(summary = "Update an existing game", description = "Updates the information of an existing game by its ID. Requires ADMIN role.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The game data to be updated. All fields are required.", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = GameRequestDTO.class), examples = @ExampleObject(name = "Eastward Update Example", summary = "Example payload for updating a game to 'Eastward'", value = "{\"gameTitle\":\"Eastward\", \"developer\":\"Pixpil\", \"publisher\":\"Chucklefish\", \"releaseYear\":\"2021-09-16\"}"))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game updated successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GameResponseDTO.class), examples = @ExampleObject(name = "Eastward Response Example", summary = "Example response for the 'Eastward' game", value = "{\"id\":1, \"gameTitle\":\"Eastward\"}"))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided. See response body for details.", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Validation Error Example", summary = "Example response for a validation error on the 'gameTitle' field.", value = "[{\"field\": \"gameTitle\", \"message\": \"O nome do jogo não pode estar em branco.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Game not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class)))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<GameResponseDTO> updateGame(
            @Parameter(description = "Unique ID of the game.") @PathVariable Long id,
            @RequestBody @Valid GameRequestDTO gameRequestDTO) {
        GameResponseDTO gameToUpdate = service.updateGameById(id, gameRequestDTO);

        return ResponseEntity.ok(gameToUpdate);
    }

    @Operation(summary = "Delete a game", description = "Deletes a game from the catalog by its ID. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Game deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Game not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class)))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteGame(@Parameter(description = "Unique ID of the game.") @PathVariable Long id) {
        service.deleteGameById(id);
    }
}
