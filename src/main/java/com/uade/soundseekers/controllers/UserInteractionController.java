package com.uade.soundseekers.controllers;

import com.uade.soundseekers.dto.MessageResponseDto;
import com.uade.soundseekers.exception.NotFoundException; // Importa la excepción correspondiente
import com.uade.soundseekers.exception.InvalidArgsException; // Importa la excepción correspondiente
import com.uade.soundseekers.service.UserInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/user-interactions")
public class UserInteractionController {

    @Autowired
    private UserInteractionService userInteractionService;

    // Endpoint for recording a like on an event
    @PostMapping("/{userId}/events/{eventId}/like")
    public ResponseEntity<MessageResponseDto> recordLike(@PathVariable Long userId, @PathVariable Long eventId) {
        try {
            return ResponseEntity.ok(userInteractionService.recordLike(userId, eventId));
        } catch (Exception e) {
            throw new InvalidArgsException("Error al registrar el like para el evento.");
        }
    }

    @DeleteMapping("/{userId}/events/{eventId}/like")
    public ResponseEntity<MessageResponseDto> deleteLike(@PathVariable Long userId, @PathVariable Long eventId) {
        try {
            return ResponseEntity.ok(userInteractionService.toggleLike(userId, eventId));
        } catch (Exception e) {
            throw new NotFoundException("No se pudo eliminar el like para el evento.");
        }
    }

    // Endpoint for recording an assist on an event
    @PostMapping("/{userId}/events/{eventId}/assist")
    public ResponseEntity<MessageResponseDto> recordAssist(@PathVariable Long userId, @PathVariable Long eventId) {
        try {
            return ResponseEntity.ok(userInteractionService.recordAssist(userId, eventId));
        } catch (Exception e) {
            throw new InvalidArgsException("Error al registrar la asistencia al evento.");
        }
    }

    @DeleteMapping("/{userId}/events/{eventId}/assist")
    public ResponseEntity<MessageResponseDto> deleteAssist(@PathVariable Long userId, @PathVariable Long eventId) {
        try {
            return ResponseEntity.ok(userInteractionService.toggleAssist(userId, eventId));
        } catch (Exception e) {
            throw new NotFoundException("No se pudo eliminar la asistencia para el evento.");
        }
    }

    @PostMapping("/{userId}/search")
    public ResponseEntity<MessageResponseDto> recordSearch(
            @PathVariable Long userId,
            @RequestParam(required = false) List<String> genres,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) LocalDateTime startDateTime,
            @RequestParam(required = false) LocalDateTime endDateTime,
            @RequestParam(required = false) Long localidadId) {
        try {
            return ResponseEntity.ok(userInteractionService.recordSearch(userId, genres, minPrice, maxPrice, startDateTime, endDateTime, localidadId));
        } catch (InvalidArgsException e) {
            // Maneja error relacionado con parámetros inválidos
            throw new InvalidArgsException("Error en los parámetros de búsqueda.");
        } catch (NotFoundException e) {
            // Maneja error si no se encuentra la búsqueda
            throw new NotFoundException("No se encontraron resultados para la búsqueda.");
        } catch (Exception e) {
            // Captura cualquier otro error
            throw new RuntimeException("Error inesperado al registrar la búsqueda.");
        }
    }
}
