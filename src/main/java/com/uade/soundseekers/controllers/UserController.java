package com.uade.soundseekers.controllers;

import com.uade.soundseekers.dto.UserDTO;
import com.uade.soundseekers.entity.User;
import com.uade.soundseekers.exception.NotFoundException; // Asegúrate de importar la excepción
import com.uade.soundseekers.exception.InvalidArgsException; // Importa la excepción correspondiente
import com.uade.soundseekers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://front-seminario.s3-website.us-east-2.amazonaws.com/")
public class UserController {

    @Autowired
    private UserService userService;

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            throw new NotFoundException("No se encontraron usuarios.");
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);

        if (user.isEmpty()) {
            throw new NotFoundException("Usuario no encontrado con ID: " + id);
        }

        return ResponseEntity.ok(user.get());
    }

    // Obtener un usuario por nombre de usuario
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.getUserByUsername(username);

        if (user.isEmpty()) {
            throw new NotFoundException("Usuario no encontrado con el nombre de usuario: " + username);
        }

        return ResponseEntity.ok(user.get());
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    // Eliminar un usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}