package io.github.manojohnsons.gamesoundtracksapi.music;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.manojohnsons.gamesoundtracksapi.doc.ApiAuthErrorResponses;
import io.github.manojohnsons.gamesoundtracksapi.exception.GlobalExceptionHandler;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.AlbumRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.AlbumResponseDTO;
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

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag(name = "Albums")
@RestController
@RequestMapping("games/{gameId}/albums")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @Operation(summary = "Add a new album", description = "Creates a new album in the catalog. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Album created successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlbumResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided. See response body for details.", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Validation Error Example", summary = "Example response for a validation error on the 'albumTitle' field.", value = "[{\"field\": \"albumTitle\", \"message\": \"O título do album não pode estar em branco.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Game not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class)))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<AlbumResponseDTO> addAlbum(
            @Parameter(description = "Unique ID of a game.") @PathVariable Long gameId,
            @RequestBody @Valid AlbumRequestDTO albumRequestDTO,
            UriComponentsBuilder uriBuilder) {
        AlbumResponseDTO newAlbum = albumService.addAlbum(gameId, albumRequestDTO);
        URI location = uriBuilder.path("games/{gameId}/albums/{albumId}")
                .buildAndExpand(gameId, newAlbum.getId())
                .toUri();

        return ResponseEntity.created(location).body(newAlbum);
    }

    @Operation(summary = "List all albums of a game", description = "Returns a list of all albums of a specific game in the catalog.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation. Returns a list of albums of a game, which may be empty if no albums are found.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AlbumResponseDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Game not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class)))
    })
    @GetMapping
    public ResponseEntity<List<AlbumResponseDTO>> listAlbuns(
            @Parameter(description = "Unique ID of the game.") @PathVariable Long gameId) {
        List<AlbumResponseDTO> albuns = albumService.listAlbumsOfAGame(gameId);

        return ResponseEntity.ok(albuns);
    }

    @Operation(summary = "Find a specific album of a game", description = "Retrieves the details of a specific album by its ID of a specific game in the catalog.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Album found successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlbumResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found - This can happen for two reasons: <br>"
                    +
                    "1) Game not found with the specified ID. <br>" +
                    "2) Album not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                            @ExampleObject(name = "Game Not Found Response Example", summary = "Example response when a game is not found in the catalog."),
                            @ExampleObject(name = "Album Not Found Response Example", summary = "Example response when a album is not found in the game.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Album with ID 99 not found in the game with ID 1\"}")
                    }))
    })
    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumResponseDTO> searchAlbumById(
            @Parameter(description = "Unique ID of the game.") @PathVariable Long gameId,
            @Parameter(description = "Unique ID of the album.") @PathVariable Long albumId) {
        AlbumResponseDTO album = albumService.findAlbumById(gameId, albumId);

        return ResponseEntity.ok(album);
    }

    @Operation(summary = "Update an existing album", description = "Updates the information of an existing album by its ID of a specific game in the catalog. Requires ADMIN role.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The album data to be updated. All fields are required.", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlbumRequestDTO.class), examples = @ExampleObject(name = "Update Album Example", summary = "Example payload for updating a album of a game.", value = "{\"albumTitle\": \"DELTARUNE Chapter 3 + 4 Soundtrack - Official\"}"))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Album updated successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AlbumResponseDTO.class), examples = @ExampleObject(name = "Album Updated Example", summary = "Example response for a updated album of a game", value = "{\"id\": 1, \"albumTitle\": \"DELTARUNE Chapter 3 + 4 Soundtrack - Official\", \"gameId\": 1}"))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided. See response body for details.", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Validation Error Example", summary = "Example response for a validation error on the 'albumTitle' field.", value = "[{\"field\": \"albumTitle\", \"message\": \"O título do album não pode estar em branco.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Not Found - This can happen for two reasons: <br>" +
                    "1) Game not found with the specified ID. <br>" +
                    "2) Album not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                            @ExampleObject(name = "Game Not Found Response Example", summary = "Example response when a game is not found in the catalog."),
                            @ExampleObject(name = "Album Not Found Response Example", summary = "Example response when a album is not found in the game.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Album with ID 99 not found in the game with ID 1\"}")
                    }))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{albumId}")
    public ResponseEntity<AlbumResponseDTO> updateAlbum(
            @Parameter(description = "Unique ID of the game.") @PathVariable Long gameId,
            @Parameter(description = "Unique ID of the album.") @PathVariable Long albumId,
            @RequestBody @Valid AlbumRequestDTO albumRequestDTO) {
        AlbumResponseDTO albumUpdated = albumService.updateAlbum(gameId, albumId, albumRequestDTO);

        return ResponseEntity.ok(albumUpdated);
    }

    @Operation(summary = "Delete a album", description = "Deletes a album from a game by its ID. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Album deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Not Found - This can happen for two reasons: <br>"
                    +
                    "1) Game not found with the specified ID. <br>" +
                    "2) Album not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                            @ExampleObject(name = "Game Not Found Response Example", summary = "Example response when a game is not found in the catalog."),
                            @ExampleObject(name = "Album Not Found Response Example", summary = "Example response when a album is not found in the game.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Album with ID 99 not found in the game with ID 1\"}")
                    }))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{albumId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteAlbum(
            @Parameter(description = "Unique ID of the game.") @PathVariable Long gameId,
            @Parameter(description = "Unique ID of the album.") @PathVariable Long albumId) {
        albumService.deleteAlbum(gameId, albumId);
    }
}
