package io.github.manojohnsons.gamesoundtracksapi.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.manojohnsons.gamesoundtracksapi.user.dtos.UserRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.user.dtos.UserResponseDTO;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Transactional
    public UserResponseDTO insertUser(UserRequestDTO userRequestDTO) {
        // TODO: Add password encryption.
        User newUser = new User(userRequestDTO.getUsername(), userRequestDTO.getPassword());
        User userSaved = repository.save(newUser);

        return new UserResponseDTO(userSaved);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User userFetched = repository.findById(id).orElseThrow(NoSuchElementException::new);

        return new UserResponseDTO(userFetched);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        List<User> allUsers = repository.findAll();

        return allUsers.stream().map(UserResponseDTO::new).toList();
    }

    @Transactional
    public UserResponseDTO updateUserById(Long id, UserRequestDTO userRequestDTO) {
        User userToUpdate = repository.findById(id).orElseThrow(NoSuchElementException::new);
        userToUpdate.setUsername(userRequestDTO.getUsername());
        userToUpdate.setPassword(userRequestDTO.getPassword()); // TODO: Encrypt new password
        User userUpdated = repository.save(userToUpdate);

        return new UserResponseDTO(userUpdated);
    }

    @Transactional
    public void deleteUserById(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuario não encontrado com o ID: " + id);
        }

        repository.deleteById(id);
    }
}
