package com.uade.soundseekers.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.uade.soundseekers.entity.Event;
import com.uade.soundseekers.service.EventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    // Obtener todos los eventos
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Obtener un evento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear un nuevo evento
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event newEvent = eventService.saveEvent(event);
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    // Actualizar un evento existente
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        try {
            Event updatedEvent = eventService.updateEvent(id, eventDetails);
            return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un evento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Buscar eventos por ubicación y género
    @GetMapping("/search")
    public ResponseEntity<List<Event>> getEventsByLocationAndGenre(@RequestParam String location, @RequestParam String genre) {
        List<Event> events = eventService.getEventsByLocationAndGenre(location, genre);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Buscar eventos por rango de fechas
    @GetMapping("/date-range")
    public ResponseEntity<List<Event>> getEventsByDateRange(@RequestParam LocalDateTime startDateTime, @RequestParam LocalDateTime endDateTime) {
        List<Event> events = eventService.getEventsByDateRange(startDateTime, endDateTime);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Buscar eventos por precio máximo
    @GetMapping("/price")
    public ResponseEntity<List<Event>> getEventsByPrice(@RequestParam Double price) {
        List<Event> events = eventService.getEventsByPriceLessThanEqual(price);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Buscar eventos por género y rango de fechas
    @GetMapping("/genre-date-range")
    public ResponseEntity<List<Event>> getEventsByGenreAndDateRange(@RequestParam String genre, @RequestParam LocalDateTime startDateTime, @RequestParam LocalDateTime endDateTime) {
        List<Event> events = eventService.getEventsByGenreAndDateRange(genre, startDateTime, endDateTime);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    // Buscar eventos por ubicación, género y rango de fechas
    @GetMapping("/search-advanced")
    public ResponseEntity<List<Event>> getEventsByLocationAndGenreAndDateRange(@RequestParam String location, @RequestParam String genre, @RequestParam LocalDateTime startDateTime, @RequestParam LocalDateTime endDateTime) {
        List<Event> events = eventService.getEventsByLocationAndGenreAndDateRange(location, genre, startDateTime, endDateTime);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}
