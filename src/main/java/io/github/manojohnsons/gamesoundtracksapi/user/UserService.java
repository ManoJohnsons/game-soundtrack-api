package io.github.manojohnsons.gamesoundtracksapi.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.manojohnsons.gamesoundtracksapi.user.dtos.UserRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.user.dtos.UserResponseDTO;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repository;

    public UserResponseDTO insertUser(UserRequestDTO userRequestDTO) {
        //TODO: Add password encryption.
        User newUser = new User(userRequestDTO.getUsername(), userRequestDTO.getPassword());
        User userSaved = repository.save(newUser);

        return new UserResponseDTO(userSaved);
    }
}
