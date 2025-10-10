package io.github.manojohnsons.gamesoundtracksapi.music;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.manojohnsons.gamesoundtracksapi.doc.ApiAuthErrorResponses;
import io.github.manojohnsons.gamesoundtracksapi.exception.GlobalExceptionHandler;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.MusicRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.MusicResponseDTO;
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

@Tag(name = "Musics")
@RestController
@RequestMapping("/games/{gameId}/albums/{albumId}/musics")
public class MusicController {

    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @Operation(summary = "Add a new music", description = "Creates a new music in the catalog. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Music created successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MusicResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided. See response body for details.", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Validation Error Example", summary = "Example response for a validation error on the 'musicTitle' field.", value = "[{\"field\": \"musicTitle\", \"message\": \"O título da música não pode estar em branco.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Not Found - This can happen for two reasons: <br>"
                    +
                    "1) Game not found with the specified ID. <br>" +
                    "2) Album not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                            @ExampleObject(name = "Game Not Found Response Example", summary = "Example response when a game is not found in the catalog."),
                            @ExampleObject(name = "Album Not Found Response Example", summary = "Example response when a album is not found in the game.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Album with ID 99 not found int the game with ID 1\"}")
                    }))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<MusicResponseDTO> addMusic(
            @Parameter(description = "Unique ID of a game.") @PathVariable Long gameId,
            @Parameter(description = "Unique ID of a album.") @PathVariable Long albumId,
            @RequestBody @Valid MusicRequestDTO musicRequestDTO,
            UriComponentsBuilder uriBuilder) {
        MusicResponseDTO newMusic = musicService.addMusic(gameId, albumId, musicRequestDTO);
        URI location = uriBuilder.path("/games/{gameId}/albums/{albumId}/musics/{musicId}")
                .buildAndExpand(gameId, albumId, newMusic.getId())
                .toUri();

        return ResponseEntity.created(location).body(newMusic);
    }

    @Operation(summary = "List all musics of a album", description = "Returns a list of all musics of a album for a specific game in the catalog.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation. Returns a list of musics of a album, which may be empty if no musics are found.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MusicResponseDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Not Found - This can happen for two reasons: <br>"
                    +
                    "1) Game not found with the specified ID. <br>" +
                    "2) Album not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                            @ExampleObject(name = "Game Not Found Response Example", summary = "Example response when a game is not found in the catalog."),
                            @ExampleObject(name = "Album Not Found Response Example", summary = "Example response when a album is not found in the game.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Album with ID 99 not found int the game with ID 1\"}")
                    }))
    })
    @GetMapping
    public ResponseEntity<List<MusicResponseDTO>> listMusics(
            @Parameter(description = "Unique ID of a game.") @PathVariable Long gameId,
            @Parameter(description = "Unique ID of a album.") @PathVariable Long albumId) {
        List<MusicResponseDTO> allMusics = musicService.listMusicsFromAlbum(gameId, albumId);

        return ResponseEntity.ok(allMusics);
    }

    @Operation(summary = "Find a specific music of a album", description = "Retrieves the details of a specific music by its ID of a specific album of a game in the catalog.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Music found successfully.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MusicResponseDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Not Found - This can happen for three reasons: <br>"
                    +
                    "1) Game not found with the specified ID. <br>" +
                    "2) Album not found with the specified ID. <br>" +
                    "3) Music not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                            @ExampleObject(name = "Game Not Found Response Example", summary = "Example response when a game is not found in the catalog."),
                            @ExampleObject(name = "Album Not Found Response Example", summary = "Example response when a album is not found in the game.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Album with ID 99 not found in the game with ID 1\"}"),
                            @ExampleObject(name = "Music Not Found Response Example", summary = "Example response when a music is not found in the album.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Music with ID 99 not found in the album with ID 1\"}")
                    }))
    })
    @GetMapping("/{musicId}")
    public ResponseEntity<MusicResponseDTO> findMusicById(
            @Parameter(description = "Unique ID of a game.") @PathVariable Long gameId,
            @Parameter(description = "Unique ID of a album.") @PathVariable Long albumId,
            @Parameter(description = "Unique ID of a music.") @PathVariable Long musicId) {
        MusicResponseDTO musicFetched = musicService.findMusicById(gameId, albumId, musicId);

        return ResponseEntity.ok(musicFetched);
    }

    @Operation(summary = "Update an existing music", description = "Updates the information of an existing music by its ID of a specific album of a game in the catalog. Requires ADMIN role.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The music data to be updated. All fields are required.", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = MusicRequestDTO.class), examples = @ExampleObject(name = "Update Album Example", summary = "Example payload for updating a music of a album.", value = "{\"musicTitle\": \"Susie\", \"durationInSeconds\": 28}"))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Music updated successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MusicResponseDTO.class), examples = @ExampleObject(name = "Music Updated Example", summary = "Example response for a updated music of a album", value = "{\"id\": 1, \"musicTitle\": \"ANOTHER HIM\", \"durationInSeconds\": 53, \"albumId\": 1}"))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided. See response body for details.", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Validation Error Example", summary = "Example response for a validation error on the 'musicTitle' field.", value = "[{\"field\": \"musicTitle\", \"message\": \"O título da música não pode estar em branco.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Not Found - This can happen for three reasons: <br>"
                    +
                    "1) Game not found with the specified ID. <br>" +
                    "2) Album not found with the specified ID. <br>" +
                    "3) Music not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                            @ExampleObject(name = "Game Not Found Response Example", summary = "Example response when a game is not found in the catalog."),
                            @ExampleObject(name = "Album Not Found Response Example", summary = "Example response when a album is not found in the game.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Album with ID 99 not found in the game with ID 1\"}"),
                            @ExampleObject(name = "Music Not Found Response Example", summary = "Example response when a music is not found in the album.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Music with ID 99 not found in the album with ID 1\"}")
                    }))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{musicId}")
    public ResponseEntity<MusicResponseDTO> updateMusicById(
            @Parameter(description = "Unique ID of a game.") @PathVariable Long gameId,
            @Parameter(description = "Unique ID of a album.") @PathVariable Long albumId,
            @Parameter(description = "Unique ID of a music.") @PathVariable Long musicId,
            @RequestBody @Valid MusicRequestDTO musicRequestDTO) {
        MusicResponseDTO musicUpdate = musicService.updateMusic(gameId, albumId, musicId, musicRequestDTO);

        return ResponseEntity.ok(musicUpdate);
    }

    @Operation(summary = "Delete a music", description = "Deletes a music from a specific album of a game by its ID. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Music deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Not Found - This can happen for three reasons: <br>"
                    +
                    "1) Game not found with the specified ID. <br>" +
                    "2) Album not found with the specified ID. <br>" +
                    "3) Music not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                            @ExampleObject(name = "Game Not Found Response Example", summary = "Example response when a game is not found in the catalog."),
                            @ExampleObject(name = "Album Not Found Response Example", summary = "Example response when a album is not found in the game.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Album with ID 99 not found in the game with ID 1\"}"),
                            @ExampleObject(name = "Music Not Found Response Example", summary = "Example response when a music is not found in the album.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Music with ID 99 not found in the album with ID 1\"}")
                    }))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{musicId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteMusicById(
            @Parameter(description = "Unique ID of a game.") @PathVariable Long gameId,
            @Parameter(description = "Unique ID of a album.") @PathVariable Long albumId,
            @Parameter(description = "Unique ID of a music.") @PathVariable Long musicId) {
        musicService.deleteMusic(gameId, albumId, musicId);
    }

    @Operation(summary = "Associate music with an artist/composer", description = "Associate a music with a artist/composer. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Relation between a music and an artist/composer created successfully."),
            @ApiResponse(responseCode = "404", description = "Not Found - This can happen for four reasons: <br>"
                    +
                    "1) Game not found with the specified ID. <br>" +
                    "2) Album not found with the specified ID. <br>" +
                    "3) Music not found with the specified ID.<br>" +
                    "4) Artist/composer not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                            @ExampleObject(name = "Game Not Found Response Example", summary = "Example response when a game is not found in the catalog."),
                            @ExampleObject(name = "Album Not Found Response Example", summary = "Example response when a album is not found in the game.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Album with ID 99 not found in the game with ID 1\"}"),
                            @ExampleObject(name = "Music Not Found Response Example", summary = "Example response when a music is not found.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Music not found with ID: 99\"}"),
                            @ExampleObject(name = "Artist/Composer Not Found Response Example", summary = "Example response when a artist/composer is not found in the catalog.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Artist/Composer not found with ID: 99\"}")
                    }))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{musicId}/composers/{composerId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void associateComposer(
            @Parameter(description = "Unique ID of a music.") @PathVariable Long musicId,
            @Parameter(description = "Unique ID of a artist/composer.") @PathVariable Long composerId) {
        musicService.associateComposer(musicId, composerId);
    }

    @Operation(summary = "Diassociate music with an artist/composer", description = "Diassociate a music with a artist/composer. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Relation between a music and an artist/composer deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Not Found - This can happen for four reasons: <br>"
                    +
                    "1) Game not found with the specified ID. <br>" +
                    "2) Album not found with the specified ID. <br>" +
                    "3) Music not found with the specified ID.<br>" +
                    "4) Artist/composer not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                            @ExampleObject(name = "Game Not Found Response Example", summary = "Example response when a game is not found in the catalog."),
                            @ExampleObject(name = "Album Not Found Response Example", summary = "Example response when a album is not found in the game.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Album with ID 99 not found in the game with ID 1\"}"),
                            @ExampleObject(name = "Music Not Found Response Example", summary = "Example response when a music is not found.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Music not found with ID: 99\"}"),
                            @ExampleObject(name = "Artist/Composer Not Found Response Example", summary = "Example response when a artist/composer is not found in the catalog.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Artist/Composer not found with ID: 99\"}")
                    }))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{musicId}/composers/{composerId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void unassociateComposer(
            @Parameter(description = "Unique ID of a music.") @PathVariable Long musicId,
            @Parameter(description = "Unique ID of a artist/composer.") @PathVariable Long composerId) {
        musicService.unassociateComposer(musicId, composerId);
    }

}
