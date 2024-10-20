package com.uade.soundseekers.service;

import com.uade.soundseekers.dto.MessageResponseDto;
import com.uade.soundseekers.entity.*;
import com.uade.soundseekers.repository.LocalidadRepository;
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

    @Autowired
    private LocalidadRepository localidadRepository;

    // Obtener todos los eventos
    @Transactional
    public List<Event> getAllEvents() {
        return eventDAO.findAll();
    }

    // Crear un nuevo evento
    @Transactional
    public MessageResponseDto createEvent(EventDTO eventDTO) {
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
            .map(genre -> MusicGenre.valueOf(genre.toUpperCase()))
            .collect(Collectors.toList()));

        Localidad localidad = localidadRepository.findById(eventDTO.getLocalidadId())
            .orElseThrow(() -> new RuntimeException("Localidad not found with ID: " + eventDTO.getLocalidadId()));
        event.setLocalidad(localidad);

        eventDAO.save(event);
        return new MessageResponseDto("Event created successfully.");
    }

    // Editar un evento existente
    @Transactional
    public MessageResponseDto updateEvent(Long id, EventDTO eventDTO) {
        Event event = eventDAO.findById(id).orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setLatitude(eventDTO.getLatitude());
        event.setLongitude(eventDTO.getLongitude());
        event.setDateTime(eventDTO.getDateTime());
        event.setPrice(eventDTO.getPrice());
        event.setGenres(eventDTO.getGenres().stream()
            .map(genre -> {
                try {
                    return MusicGenre.valueOf(genre.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid genre: " + genre);
                }
            })
            .collect(Collectors.toList()));
        Localidad localidad = localidadRepository.findById(eventDTO.getLocalidadId())
            .orElseThrow(() -> new RuntimeException("Localidad not found with ID: " + eventDTO.getLocalidadId()));
        event.setLocalidad(localidad);
        eventDAO.update(event);
        return new MessageResponseDto("Event updated successfully.");
    }

    // Eliminar un evento
    @Transactional
    public MessageResponseDto deleteEvent(Long id) {
        eventDAO.deleteById(id);
        return new MessageResponseDto("Event deleted successfully.");

    }

    // Filtros avanzados
    @Transactional
    public List<Event> getEventsByFilters(String name, List<MusicGenre> genres, LocalDateTime startDate, LocalDateTime endDate, Double minPrice, Double maxPrice) {
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