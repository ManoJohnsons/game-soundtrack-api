package io.github.manojohnsons.gamesoundtracksapi.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.manojohnsons.gamesoundtracksapi.exception.ResourceAlreadyExistsException;
import io.github.manojohnsons.gamesoundtracksapi.exception.ResourceNotFoundException;
import io.github.manojohnsons.gamesoundtracksapi.music.Music;
import io.github.manojohnsons.gamesoundtracksapi.music.MusicRepository;
import io.github.manojohnsons.gamesoundtracksapi.user.dtos.UserRequestDTO;
import io.github.manojohnsons.gamesoundtracksapi.user.dtos.UserResponseDTO;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO insertUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new ResourceAlreadyExistsException("Username '" + userRequestDTO.getUsername() + "' já está em uso.");
        }
        var encryptedPassword = passwordEncoder.encode(userRequestDTO.getPassword());
        User newUser = new User(userRequestDTO.getUsername(), encryptedPassword, Role.USER);
        User userSaved = userRepository.save(newUser);

        return new UserResponseDTO(userSaved);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User userFetched = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));

        return new UserResponseDTO(userFetched);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        List<User> allUsers = userRepository.findAll();

        return allUsers.stream().map(UserResponseDTO::new).toList();
    }

    @Transactional
    @PreAuthorize("principal.getId() == #id or hasRole('ADMIN')")
    public UserResponseDTO updateUserById(Long id, UserRequestDTO userRequestDTO) {
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));
        if (!userToUpdate.getUsername().equals(userRequestDTO.getUsername()) &&
                userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new ResourceAlreadyExistsException(
                    "Username '" + userRequestDTO.getUsername() + "' já pertence a outro usuário.");
        }
        userToUpdate.setUsername(userRequestDTO.getUsername());
        userToUpdate.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        User userUpdated = userRepository.save(userToUpdate);

        return new UserResponseDTO(userUpdated);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado com o ID: " + id);
        }

        userRepository.deleteById(id);
    }

    @Transactional
    public void favoriteMusic(String username, Long musicId) {
        User user = findAndValidateUser(username);
        Music music = findMusic(musicId);
        user.getFavoriteMusics().add(music);
        userRepository.save(user);
    }

    @Transactional
    public void unfavoriteMusic(String username, Long musicId) {
        User user = findAndValidateUser(username);
        Music music = findMusic(musicId);
        user.getFavoriteMusics().remove(music);
        userRepository.save(user);
    }

    private User findAndValidateUser(String username) {
        User user = (User) userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("Usuário não encontrado!");
        }

        return user;
    }

    private Music findMusic(Long musicId) {
        Music music = musicRepository.findById(musicId)
                .orElseThrow(() -> new ResourceNotFoundException("Música não encontrada com o ID: " + musicId));

        return music;
    }
}
