package com.uade.soundseekers.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uade.soundseekers.entity.User;
import com.uade.soundseekers.repository.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

    // Crear o guardar un nuevo usuario
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Actualizar un usuario existente
    public User updateUser(Long id, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEmail(userDetails.getEmail());
            user.setName(userDetails.getName());
            user.setPassword(userDetails.getPassword());
            user.setLastName(userDetails.getLastName());
            user.setUsername(userDetails.getUsername());
            user.setEdad(userDetails.getEdad());
            user.setRole(userDetails.getRole());
            return userRepository.save(user);
        } else {
            throw new RuntimeException("Usuario no encontrado con el ID: " + id);
        }
    }

    // Eliminar un usuario por ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}