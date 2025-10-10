package io.github.manojohnsons.gamesoundtracksapi.music;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.manojohnsons.gamesoundtracksapi.music.dtos.MusicFilterDTO;
import io.github.manojohnsons.gamesoundtracksapi.music.dtos.MusicResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Musics")
@RestController
@RequestMapping("/musics")
public class MusicSearchController {

    private final MusicService musicService;

    public MusicSearchController(MusicService musicService) {
        this.musicService = musicService;
    }

    @Operation(summary = "List all musics in the catalog", description = "Returns a list of all musics in the catalog. The list can be filtered by game title, music title or a composer name (case-insensitive, partial match).")
    @ApiResponse(responseCode = "200", description = "Successful operation. Returns a list of musics, which may be empty if no musics are found or match the criteria.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MusicResponseDTO.class))))
    @GetMapping
    public ResponseEntity<List<MusicResponseDTO>> search(@ParameterObject MusicFilterDTO filterDTO) {
        List<MusicResponseDTO> result = musicService.search(filterDTO);
        return ResponseEntity.ok(result);
    }

}
