package io.github.manojohnsons.gamesoundtracksapi.music;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.manojohnsons.gamesoundtracksapi.doc.ApiAuthErrorResponses;
import io.github.manojohnsons.gamesoundtracksapi.exception.GlobalExceptionHandler;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.ComposerRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.ComposerResponseDTO;
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

@Tag(name = "Composers")
@RestController
@RequestMapping("/composers")
public class ComposerController {

    @Autowired
    private ComposerService service;

    @Operation(summary = "Add a new artist/composer", description = "Creates a new artist or composer in the catalog. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Artist/composer created successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ComposerResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided. See response body for details.", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Validation Error Example", summary = "Example response for a validation error on the 'name' field.", value = "[{\"field\": \"name\", \"message\": \"O nome do artista não pode estar em branco.\"}]")))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<ComposerResponseDTO> insertComposer(
            @RequestBody @Valid ComposerRequestDTO composerRequestDTO,
            UriComponentsBuilder uriBuilder) {
        ComposerResponseDTO newComposer = service.insertComposer(composerRequestDTO);
        URI location = uriBuilder.path("/composers/{id}").buildAndExpand(newComposer.getId()).toUri();

        return ResponseEntity.created(location).body(newComposer);
    }

    @Operation(summary = "List all artists/composers", description = "Returns a list of all artists or composers in the catalog.")
    @ApiResponse(responseCode = "200", description = "Successful operation. Returns a list of artists or composers, which may be empty if no artist or composers are found.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ComposerResponseDTO.class))))
    @GetMapping
    public ResponseEntity<List<ComposerResponseDTO>> searchAllComposers() {
        List<ComposerResponseDTO> composers = service.searchAllComposers();

        return ResponseEntity.ok(composers);
    }

    @Operation(summary = "Find a artist/composer by its ID", description = "Retrieves the details of a specific artist or composer by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artist/composer found successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ComposerResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Artist/composer not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "Artist/Composer Not Found Response Example", summary = "Example response when a artist or composer is not found in the catalog.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Artist/Composer not found with the specified ID: 99\"}")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ComposerResponseDTO> searchComposerById(
            @Parameter(description = "Unique ID of the artist/composer.") @PathVariable Long id) {
        ComposerResponseDTO composerFetched = service.searchComposerById(id);

        return ResponseEntity.ok(composerFetched);
    }

    @Operation(summary = "Update an existing artist/composer", description = "Updates the information of an existing artist or composer by its ID. Requires ADMIN role.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The artist/composer data to be updated. All fields are required.", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = ComposerRequestDTO.class), examples = @ExampleObject(name = "Artist/Composer Update Example", summary = "Example payload for updating a artist/composer to 'Laura Shigihara'", value = "{\"name\":\"Laura Shigihara\"}"))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Artist/composer updated successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ComposerResponseDTO.class), examples = @ExampleObject(name = "Artist/Composer Response Example", summary = "Example response for the update artist/composer", value = "{\"id\":1, \"name\":\"Laura Shigihara\"}"))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided. See response body for details.", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Validation Error Example", summary = "Example response for a validation error on the 'name' field.", value = "[{\"field\": \"name\", \"message\": \"O nome do artista não pode estar em branco.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Artist/composer not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "Artist/Composer Not Found Response Example", summary = "Example response when a artist or composer is not found in the catalog.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Artist/Composer not found with the specified ID: 99\"}")))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<ComposerResponseDTO> updateComposer(
            @Parameter(description = "Unique ID of the artist/composer.") @PathVariable Long id,
            @RequestBody @Valid ComposerRequestDTO composerRequestDTO) {
        ComposerResponseDTO composerToUpdate = service.updateComposerById(id, composerRequestDTO);

        return ResponseEntity.ok(composerToUpdate);
    }

    @Operation(summary = "Delete a artist/composer", description = "Deletes a artist/composer from the catalog. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Artist/composer deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Artist/composer not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "Artist/Composer Not Found Response Example", summary = "Example response when a artist or composer is not found in the catalog.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Artist/Composer not found with the specified ID: 99\"}")))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteComposer(
            @Parameter(description = "Unique ID of the artist/composer.") @PathVariable Long id) {
        service.deleteComposerById(id);
    }
}
