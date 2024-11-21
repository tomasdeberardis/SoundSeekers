package com.uade.soundseekers.service;
import com.uade.soundseekers.dto.MessageResponseDto;
import com.uade.soundseekers.dto.UserDTO;
import com.uade.soundseekers.entity.Localidad;
import com.uade.soundseekers.entity.MusicGenre;
import com.uade.soundseekers.repository.LocalidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uade.soundseekers.entity.User;
import com.uade.soundseekers.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocalidadRepository localidadRepository;

    // Obtener todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Obtener un usuario por ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Obtener un usuario por nombre de usuario
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Actualizar un usuario existente
    public MessageResponseDto updateUser(Long id, UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(userDTO.getEmail());
            user.setName(userDTO.getName());
            user.setLastName(userDTO.getLastName());
            user.setUsername(userDTO.getUsername());
            user.setEdad(userDTO.getEdad());

            Localidad localidad = localidadRepository.findById(userDTO.getLocalidadId())
                .orElseThrow(() -> new RuntimeException("Localidad con ID " + userDTO.getLocalidadId()+" no existe"));
            user.setLocalidad(localidad);

            List<MusicGenre> generosMusicales = userDTO.getGenres().stream()
                .map(genre -> {
                    try {
                        return MusicGenre.valueOf(genre.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Género Inválido: " + genre);
                    }
                })
                .collect(Collectors.toList());
            user.setGenerosMusicalesPreferidos(generosMusicales);

            userRepository.save(user);
            return new MessageResponseDto("Usuario actualizado exitosamente.");
        } else {
            throw new RuntimeException("Usuario no encontrado con el ID: " + id);
        }
    }

    // Eliminar un usuario por ID
    public MessageResponseDto deleteUser(Long id) {
        userRepository.deleteById(id);
        return new MessageResponseDto("Usuario eliminado exitosamente.");
    }
}