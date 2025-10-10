package io.github.manojohnsons.gamesoundtracksapi.game;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.manojohnsons.gamesoundtracksapi.doc.ApiAuthErrorResponses;
import io.github.manojohnsons.gamesoundtracksapi.exception.GlobalExceptionHandler;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.AvailabilityRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.GameResponseDTO;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.PlatformRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.game.dtos.PlatformResponseDTO;
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

import org.springframework.web.bind.annotation.*;

@Tag(name = "Platforms")
@RestController
@RequestMapping("/platforms")
public class PlatformController {

    @Autowired
    private PlatformService service;

    @Operation(summary = "Add a new platform", description = "Creates a new platform in the catalog. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Platform created successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlatformResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided. See response body for details.", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Validation Error Example", summary = "Example response for a validation error on the 'platformName' field.", value = "[{\"field\": \"platformName\", \"message\": \"O nome da plataforma não pode estar em branco.\"}]"))),
            @ApiResponse(responseCode = "409", description = "Platform already exists in the catalog.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "Conflict Error Example", summary = "Example response for a conflict error in this route when attempting to create the platform that already exists in the catalog.", value = "{\"status\":\"CONFLICT\", \"message\":\"Platform name Steam is already registered.\"}")))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<PlatformResponseDTO> insertPlatform(
            @RequestBody @Valid PlatformRequestDTO platformRequestDTO,
            UriComponentsBuilder uriBuilder) {
        PlatformResponseDTO newPlatform = service.insertPlatform(platformRequestDTO);
        URI location = uriBuilder.path("/platforms/{id}").buildAndExpand(newPlatform.getId()).toUri();

        return ResponseEntity.created(location).body(newPlatform);
    }

    @Operation(summary = "List all platforms", description = "Returns a list of all platforms in the catalog.")
    @ApiResponse(responseCode = "200", description = "Successful operation. Returns a list of platforms, which may be empty if no platforms are found.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = PlatformResponseDTO.class))))
    @GetMapping
    public ResponseEntity<List<PlatformResponseDTO>> getAllPlatforms() {
        List<PlatformResponseDTO> allPlatforms = service.getAllPlatforms();

        return ResponseEntity.ok(allPlatforms);
    }

    @Operation(summary = "Find a platform by its ID", description = "Retrieves the details of a specific platform by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Platform found successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlatformResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Platform not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "Platform Not Found Response Example", summary = "Example response when a platform is not found in the catalog.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Platform not found with the specified ID: 99\"}")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlatformResponseDTO> getPlatformById(
            @Parameter(description = "Unique ID of the platform.") @PathVariable Long id) {
        PlatformResponseDTO platformFetched = service.getPlatformById(id);

        return ResponseEntity.ok(platformFetched);
    }

    @Operation(summary = "Update an existing platform", description = "Updates the information of an existing platform by its ID. Requires ADMIN role.", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The platform data to be updated. All fields are required.", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = PlatformRequestDTO.class), examples = @ExampleObject(name = "Epic Games Store Update Example", summary = "Example payload for updating a platform to 'Epic Games Store'", value = "{\"platformName\":\"Epic Games Store\"}"))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Platform updated successfully.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GameResponseDTO.class), examples = @ExampleObject(name = "Epic Games Store Response Example", summary = "Example response for the 'Epic Games Store' platform.", value = "{\"id\":1, \"platformName\":\"Epic Games Store\"}"))),
            @ApiResponse(responseCode = "400", description = "Invalid input data provided. See response body for details.", content = @Content(mediaType = "application/json", examples = @ExampleObject(name = "Validation Error Example", summary = "Example response for a validation error on the 'platformName' field.", value = "[{\"field\": \"platformName\", \"message\": \"O nome da plataforma não pode estar em branco.\"}]"))),
            @ApiResponse(responseCode = "404", description = "Platform not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "Platform Not Found Response Example", summary = "Example response when a platform is not found in the catalog.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Platform not found with the specified ID: 99\"}"))),
            @ApiResponse(responseCode = "409", description = "Platform already exists in the catalog.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "Conflict Error Example", summary = "Example response for a conflict error in this route when attempting to update the platform that already exists in the catalog.", value = "{\"status\":\"CONFLICT\", \"message\":\"Platform name Epic Games Store is already registered.\"}")))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<PlatformResponseDTO> updatePlatform(
            @Parameter(description = "Unique ID of the platform.") @PathVariable Long id,
            @RequestBody @Valid PlatformRequestDTO platformRequestDTO) {
        PlatformResponseDTO platformUpdated = service.updatePlatform(id, platformRequestDTO);

        return ResponseEntity.ok(platformUpdated);
    }

    @Operation(summary = "Delete a platform", description = "Deletes a platform from the catalog by its ID. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Platform deleted successfully."),
            @ApiResponse(responseCode = "404", description = "Platform not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = @ExampleObject(name = "Platform Not Found Response Example", summary = "Example response when a platform is not found in the catalog.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Platform not found with the specified ID: 99\"}")))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePlatform(
            @Parameter(description = "Unique ID of the platform.") @PathVariable Long id) {
        service.deletePlatform(id);
    }

    @Operation(summary = "Add an available game to a platform", description = "Associate a platform with a game available for purchase. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Relation between a platform and an available game created successfully."),
            @ApiResponse(responseCode = "404", description = "Not Found - This can happen for two reasons: <br>"
                    +
                    "1) Platform not found with the specified ID. <br>" +
                    "2) Game not found with the specified ID.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalExceptionHandler.ApiError.class), examples = {
                            @ExampleObject(name = "Game Not Found Response Example", summary = "Example response when a game is not found in the catalog."),
                            @ExampleObject(name = "Platform Not Found Response Example", summary = "Example response when a platform is not found in the catalog.", value = "{\"status\":\"NOT_FOUND\", \"message\":\"Platform not found with the specified ID: 99\"}")
                    }))
    })
    @ApiAuthErrorResponses
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{platformId}/games/{gameId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void addAvailableGame(
            @Parameter(description = "Unique ID of the platform.") @PathVariable Long platformId,
            @Parameter(description = "Unique ID of the game.") @PathVariable Long gameId,
            @RequestBody @Valid AvailabilityRequestDTO availabilityRequestDTO) {
        service.addGameAvailable(platformId, gameId, availabilityRequestDTO);
    }

}
