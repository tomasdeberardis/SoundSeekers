package com.uade.soundseekers.service;

import com.uade.soundseekers.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uade.soundseekers.dto.EventDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private UserService userService;

    // Obtener todos los eventos
    @Transactional
    public List<Event> getAllEvents() {
        return eventDAO.findAll();
    }

    @Transactional
    public Event createEvent(Event event) {
        eventDAO.save(event);
        return event;
    }

    // Crear un nuevo evento
    @Transactional
    public Event createEventFromDTO(EventDTO eventDTO) {
        Optional<User> optionalOrganizer = userService.getUserById(eventDTO.getOrganizerId()); // Buscar organizador por ID
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setLatitude(eventDTO.getLatitude());
        event.setLongitude(eventDTO.getLongitude());
        event.setDateTime(eventDTO.getDateTime());
        event.setPrice(eventDTO.getPrice());

        if (optionalOrganizer.isPresent()) {
            User organizer = optionalOrganizer.get();
            event.setOrganizer(organizer);
        } else {
            throw new RuntimeException("Organizador no encontrado con el ID: " + eventDTO.getOrganizerId());
        }

        event.setGenres(eventDTO.getGenres().stream()
            .map(genre -> musicGenre.valueOf(genre.toUpperCase()))
            .collect(Collectors.toList()));

        eventDAO.save(event);
        return event;
    }

    // Editar un evento existente
    @Transactional
    public Event updateEvent(Long id, Event eventDetails) {
        Event event = eventDAO.findById(id).orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        event.setName(eventDetails.getName());
        event.setDescription(eventDetails.getDescription());
        event.setLatitude(eventDetails.getLatitude());
        event.setLongitude(eventDetails.getLongitude());
        event.setDateTime(eventDetails.getDateTime());
        event.setPrice(eventDetails.getPrice());
        event.setGenres(eventDetails.getGenres());
        event.setAttendees(eventDetails.getAttendees());
        eventDAO.update(event);
        return event;
    }

    // Eliminar un evento
    @Transactional
    public void deleteEvent(Long id) {
        eventDAO.deleteById(id);
    }

    // Filtros avanzados
    @Transactional
    public List<Event> getEventsByFilters(String name, List<musicGenre> genres, LocalDateTime startDate, LocalDateTime endDate, Double minPrice, Double maxPrice) {
        return eventDAO.findByAdvancedFilters(name, genres, startDate, endDate, minPrice, maxPrice);
    }

    // Búsqueda de eventos por proximidad (latitud, longitud, radio)
    @Transactional
    public List<Event> searchEventsByProximity(Double lat, Double lng, Double radius) {
        return eventDAO.findByProximity(lat, lng, radius);
    }

    @Transactional
    public void addImageToEvent(Long eventId, Image image) {
        Optional<Event> eventOptional = eventDAO.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.getImages().add(image);
            image.setEvent(event);
            eventDAO.update(event);
        }
    }

    @Transactional
    public void removeImageFromEvent(Long eventId, Long imageId) {
        Optional<Event> eventOptional = eventDAO.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.getImages().removeIf(image -> Objects.equals(image.getId(), imageId));
            eventDAO.update(event);
        }
    }
}