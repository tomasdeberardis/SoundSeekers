package com.uade.soundseekers.controllers;

import com.uade.soundseekers.entity.MusicGenre;
import com.uade.soundseekers.exception.NotFoundException; // Asegúrate de importar la excepción
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/music-genres")
public class MusicGenreController {

    @GetMapping
    public ResponseEntity<List<MusicGenre>> getAllMusicGenres() {
        // Obtener todos los géneros musicales
        List<MusicGenre> genres = Arrays.asList(MusicGenre.values());

        // Si no hay géneros musicales disponibles, lanzar una excepción NotFound
        if (genres.isEmpty()) {
            throw new NotFoundException("No se encontraron géneros musicales.");
        }

        // Retornar la lista de géneros musicales si todo está correcto
        return ResponseEntity.ok(genres);
    }
}
