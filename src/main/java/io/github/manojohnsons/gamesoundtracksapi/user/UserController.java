package io.github.manojohnsons.gamesoundtracksapi.user;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.github.manojohnsons.gamesoundtracksapi.user.dtos.UserRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.user.dtos.UserResponseDTO;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<UserResponseDTO> insertUser(@RequestBody @Valid UserRequestDTO userRequestDTO,
            UriComponentsBuilder uriBuilder) {
        UserResponseDTO newUser = service.insertUser(userRequestDTO);
        URI location = uriBuilder.path("/users/{id}").buildAndExpand(newUser.getId()).toUri();

        return ResponseEntity.created(location).body(newUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> allUsers = service.getAllUsers();

        return ResponseEntity.ok(allUsers);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO userFetched = service.getUserById(id);

        return ResponseEntity.ok(userFetched);
    }
    

}
