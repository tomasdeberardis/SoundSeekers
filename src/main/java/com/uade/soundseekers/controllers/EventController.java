package com.uade.soundseekers.controllers;

import com.uade.soundseekers.dto.EventDTO;
import com.uade.soundseekers.entity.Event;
import com.uade.soundseekers.entity.Image;
import com.uade.soundseekers.entity.MusicGenre;
import com.uade.soundseekers.exception.InvalidArgsException;
import com.uade.soundseekers.exception.NotFoundException;
import com.uade.soundseekers.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "http://front-seminario.s3-website.us-east-2.amazonaws.com/")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // Obtener todos los eventos
    @GetMapping
    public List<Event> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        if (events.isEmpty()) {
            throw new NotFoundException("No se encontraron eventos.");
        }
        return events;
    }

    // Crear un nuevo evento
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventDTO eventDTO) {
        if (eventDTO == null || eventDTO.getName() == null || eventDTO.getGenres() == null) {
            throw new InvalidArgsException("Datos inválidos para crear el evento.");
        }
        return ResponseEntity.ok(eventService.createEvent(eventDTO));
    }

    // Editar un evento existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventDTO));
    }

    // Eliminar un evento
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.deleteEvent(id));
    }

    // Filtros avanzados
    @GetMapping("/filters")
    public List<Event> getEventsByFilters(@RequestParam(required = false) String name,
                                          @RequestParam(required = false) List<String> genres,
                                          @RequestParam(required = false) LocalDateTime startDate,
                                          @RequestParam(required = false) LocalDateTime endDate,
                                          @RequestParam(required = false) Double minPrice,
                                          @RequestParam(required = false) Double maxPrice) {

        List<MusicGenre> genreEnumList = null;
        if (genres != null) {
            try {
                genreEnumList = genres.stream()
                                      .map(genre -> MusicGenre.valueOf(genre.toUpperCase())) 
                                      .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                throw new InvalidArgsException("Género musical no válido.");
            }
        }
        return eventService.getEventsByFilters(name, genreEnumList, startDate, endDate, minPrice, maxPrice);
    }

    // Búsqueda de eventos por proximidad (latitud, longitud, radio)
    @GetMapping("/proximity")
    public List<Event> searchEventsByProximity(@RequestParam Double lat, @RequestParam Double lng, @RequestParam Double radius) {
        if (lat == null || lng == null || radius == null) {
            throw new InvalidArgsException("Faltan parámetros para la búsqueda por proximidad.");
        }
        return eventService.searchEventsByProximity(lat, lng, radius);
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<Void> addImageToEvent(@PathVariable Long id, @RequestBody Image image) {
        if (image == null) {
            throw new InvalidArgsException("Imagen no válida.");
        }
        eventService.addImageToEvent(id, image);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/images/{imageId}")
    public ResponseEntity<Void> removeImageFromEvent(@PathVariable Long id, @PathVariable Long imageId) {
        if (imageId == null) {
            throw new InvalidArgsException("ID de imagen no válido.");
        }
        eventService.removeImageFromEvent(id, imageId);
        return ResponseEntity.ok().build();
    }

    // Listado de eventos por artista
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<Event>> getEventsByArtistId(@PathVariable Long artistId) {
        if (artistId == null) {
            throw new InvalidArgsException("ID de artista no válido.");
        }
        List<Event> events = eventService.getEventsByArtistId(artistId);
        if (events.isEmpty()) {
            throw new NotFoundException("No se encontraron eventos para este artista.");
        }
        return ResponseEntity.ok(events);
    }

    @GetMapping("/user/{userId}/attending")
    public List<Event> getEventsByUserAttendance(@PathVariable Long userId) {
        if (userId == null) {
            throw new InvalidArgsException("ID de usuario no válido.");
        }
        return eventService.getEventsByUserAttendance(userId);
    }

    @GetMapping("/user/{userId}/likes")
    public List<Event> getEventsByUserLike(@PathVariable Long userId) {
        if (userId == null) {
            throw new InvalidArgsException("ID de usuario no válido.");
        }
        return eventService.getEventsByUserLikes(userId);
    }
}
