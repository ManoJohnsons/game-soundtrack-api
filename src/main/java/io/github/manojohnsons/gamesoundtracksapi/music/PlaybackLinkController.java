package io.github.manojohnsons.gamesoundtracksapi.music;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.manojohnsons.gamesoundtracksapi.doc.ApiAuthErrorResponses;
import io.github.manojohnsons.gamesoundtracksapi.exception.GlobalExceptionHandler;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.PlaybackLinkRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.PlaybackLinkResponseDTO;
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

@Tag(name = "Musics")
@RestController
@RequestMapping("musics/{musicId}/links")
public class PlaybackLinkController {

    private final PlaybackLinkService playbackLinkService;

    public PlaybackLinkController(PlaybackLinkService playbackLinkService) {
        this.playbackLinkService = playbackLinkService;
    }

    @Operation(summary = "Add a new playback link for a music", description = "Creates a new playback link to a music in the catalog. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Link created successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlaybackLinkResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided. See response body for details.", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Validation Error Example", summary = "Example response for a validation error on the 'musicUrl' field.", value = "[{\"field\": \"musicUrl\", \"message\": \"A URL fornecida é inválida.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Music not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "Music Not Found Response", summary = "Example response when a music is not found in the catalog.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Music not found in with the specified ID: 99\"}")))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<PlaybackLinkResponseDTO> addLink(
            @Parameter(description = "Unique ID of the music.") @PathVariable Long musicId,
            @RequestBody @Valid PlaybackLinkRequestDTO dto, UriComponentsBuilder uriBuilder) {
        PlaybackLinkResponseDTO newLink = playbackLinkService.addLink(musicId, dto);
        URI location = uriBuilder.path("musics/{musicId}/links/{linkId}")
                .buildAndExpand(musicId, newLink.getId())
                .toUri();

        return ResponseEntity.created(location).body(newLink);
    }

    @Operation(summary = "List all playback links of a music", description = "Returns a list of all links to listen a music in the catalog.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation. Returns a list of playback links of a music, which may be empty if no links are found.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PlaybackLinkResponseDTO.class)))),
            @ApiResponse(responseCode = "404", description = "Music not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "Music Not Found Response", summary = "Example response when a music is not found in the catalog.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Music not found in with the specified ID: 99\"}")))
    })
    @GetMapping
    public ResponseEntity<List<PlaybackLinkResponseDTO>> listLinks(
            @Parameter(description = "Unique ID of the music.") @PathVariable Long musicId) {
        List<PlaybackLinkResponseDTO> allLinks = playbackLinkService.listAllLinksOfAMusic(musicId);

        return ResponseEntity.ok(allLinks);
    }

    @Operation(summary = "Find a playback link of a music by its ID", description = "Retrieves the link of a specific playback link of a music by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Playback link found successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlaybackLinkResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found - This can happen for two reasons: <br>"
                    +
                    "1) Music not found with the specified ID. <br>" +
                    "2) Playback link not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                            @ExampleObject(name = "Music Not Found Response", summary = "Example response when a music is not found in the catalog.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Music not found in with the specified ID: 99\"}"),
                            @ExampleObject(name = "Playback Link Not Found Response Example", summary = "Example response when a playback link is not found for a music.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Music link with ID 99 not found for a music with ID 1\"}")
                    }))
    })
    @GetMapping("/{linkId}")
    public ResponseEntity<PlaybackLinkResponseDTO> getOneLink(
            @Parameter(description = "Unique ID of the music.") @PathVariable Long musicId,
            @Parameter(description = "Unique ID of the playback link.") @PathVariable Long linkId) {
        PlaybackLinkResponseDTO link = playbackLinkService.getOneLinkOfAMusic(musicId, linkId);

        return ResponseEntity.ok(link);
    }

    @Operation(summary = "Update an existing playback link of a music", description = "Updates the information of an existing playback link of a music by its ID. Requires ADMIN role.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The link data to be updated. All fields are required.", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlaybackLinkRequestDTO.class), examples = @ExampleObject(name = "Update Playback Link Example", summary = "Example payload for updating a playback link to 'Spotify'", value = "{\"platformName\":\"Spotify\", \"musicUrl\":\"https://open.spotify.com/track/63K6koyn1kUIXj4soQ9wNl?si=d75ea3652db24657\"}"))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Playback link updated successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlaybackLinkResponseDTO.class), examples = @ExampleObject(name = "Playback Link Updated Response Example", summary = "Example response for the updated playback link.", value = "{\"id\":1, \"platformName\":\"Spotify\", \"musicUrl\":\"https://open.spotify.com/track/63K6koyn1kUIXj4soQ9wNl?si=d75ea3652db24657\"}"))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided. See response body for details.", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Validation Error Example", summary = "Example response for a validation error on the 'musicUrl' field.", value = "[{\"field\": \"musicUrl\", \"message\": \"A URL fornecida é inválida.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Not Found - This can happen for two reasons: <br>"
                    +
                    "1) Music not found with the specified ID. <br>" +
                    "2) Playback link not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                            @ExampleObject(name = "Music Not Found Response", summary = "Example response when a music is not found in the catalog.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Music not found in with the specified ID: 99\"}"),
                            @ExampleObject(name = "Playback Link Not Found Response Example", summary = "Example response when a playback link is not found for a music.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Music link with ID 99 not found for a music with ID 1\"}")
                    }))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{linkId}")
    public ResponseEntity<PlaybackLinkResponseDTO> updateLink(
            @Parameter(description = "Unique ID of the music.") @PathVariable Long musicId,
            @Parameter(description = "Unique ID of the playback link.") @PathVariable Long linkId,
            @RequestBody @Valid PlaybackLinkRequestDTO dto) {
        PlaybackLinkResponseDTO linkUpdate = playbackLinkService.updateLinkOfAMusic(musicId, linkId, dto);

        return ResponseEntity.ok(linkUpdate);
    }

    @Operation(summary = "Delete a playback link of a music", description = "Deletes a playback link of a music from the catalog by its ID. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Playback link deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Not Found - This can happen for two reasons: <br>"
                    +
                    "1) Music not found with the specified ID. <br>" +
                    "2) Playback link not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                            @ExampleObject(name = "Music Not Found Response", summary = "Example response when a music is not found in the catalog.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Music not found in with the specified ID: 99\"}"),
                            @ExampleObject(name = "Playback Link Not Found Response Example", summary = "Example response when a playback link is not found for a music.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Music link with ID 99 not found for a music with ID 1\"}")
                    }))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{linkId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteLink(
            @Parameter(description = "Unique ID of the music.") @PathVariable Long musicId,
            @Parameter(description = "Unique ID of the playback link.") @PathVariable Long linkId) {
        playbackLinkService.deleteLink(musicId, linkId);
    }
}
