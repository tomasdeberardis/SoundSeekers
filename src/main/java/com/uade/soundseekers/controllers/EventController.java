package com.uade.soundseekers.controllers;

import com.uade.soundseekers.dto.EventDTO;
import com.uade.soundseekers.entity.Event;
import com.uade.soundseekers.entity.Image;
import com.uade.soundseekers.entity.musicGenre;
import com.uade.soundseekers.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    // Obtener todos los eventos
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // Crear un nuevo evento
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody EventDTO eventDTO) {
        Event createdEvent = eventService.createEventFromDTO(eventDTO);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    // Editar un evento existente
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        Event updatedEvent = eventService.updateEvent(id, eventDetails);
        return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
    }

    // Eliminar un evento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Filtros avanzados
    @GetMapping("/filters")
    public List<Event> getEventsByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<String> genres,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice)
             {
                 // Convierte la lista de Strings a una lista de Enums manualmente
                 List<musicGenre> genreEnumList = null;
                 if (genres != null) {
                     genreEnumList = genres.stream()
                             .map(genre -> musicGenre.valueOf(genre.toUpperCase())) // Convierte cada String a su Enum correspondiente
                             .collect(Collectors.toList());
                 }
        return eventService.getEventsByFilters(name,genreEnumList, startDate, endDate, minPrice, maxPrice);
    }

    // Búsqueda de eventos por proximidad (latitud, longitud, radio)
    @GetMapping("/proximity")
    public List<Event> searchEventsByProximity(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam Double radius
    ) {
        return eventService.searchEventsByProximity(lat, lng, radius);
    }


    @PostMapping("/{id}/images")
    public ResponseEntity<Void> addImageToEvent(@PathVariable Long id, @RequestBody Image image) {
        eventService.addImageToEvent(id, image);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/images/{imageId}")
    public ResponseEntity<Void> removeImageFromEvent(@PathVariable Long id, @PathVariable Long imageId) {
        eventService.removeImageFromEvent(id, imageId);
        return ResponseEntity.ok().build();
    }
}