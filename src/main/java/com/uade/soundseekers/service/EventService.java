package com.uade.soundseekers.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uade.soundseekers.entity.Event;
import com.uade.soundseekers.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Obtener todos los eventos
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Obtener evento por ID
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    // Guardar un evento
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    // Actualizar un evento
    public Event updateEvent(Long id, Event eventDetails) {
        Optional<Event> optionalEvent = eventRepository.findById(id);

        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setName(eventDetails.getName());
            event.setDescription(eventDetails.getDescription());
            event.setLocation(eventDetails.getLocation());
            event.setGenre(eventDetails.getGenre());
            event.setDateTime(eventDetails.getDateTime());
            event.setPrice(eventDetails.getPrice());
            return eventRepository.save(event);
        } else {
            throw new RuntimeException("Evento no encontrado con el ID: " + id);
        }
    }

    // Eliminar un evento por ID
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    // Buscar eventos por ubicación y género musical
    public List<Event> getEventsByLocationAndGenre(String location, String genre) {
        return eventRepository.findByLocationAndGenre(location, genre);
    }

    // Buscar eventos por rango de fechas
    public List<Event> getEventsByDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return eventRepository.findByDateTimeBetween(startDateTime, endDateTime);
    }

    // Buscar eventos organizados por un usuario específico
    public List<Event> getEventsByOrganizer(Long organizerId) {
        return eventRepository.findByOrganizerId(organizerId);
    }

    // Buscar eventos por precio máximo
    public List<Event> getEventsByPriceLessThanEqual(Double price) {
        return eventRepository.findByPriceLessThanEqual(price);
    }

    // Buscar eventos por género y rango de fechas
    public List<Event> getEventsByGenreAndDateRange(String genre, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return eventRepository.findByGenreAndDateTimeBetween(genre, startDateTime, endDateTime);
    }

    // Buscar eventos por ubicación, género y rango de fechas
    public List<Event> getEventsByLocationAndGenreAndDateRange(String location, String genre, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return eventRepository.findByLocationAndGenreAndDateTimeBetween(location, genre, startDateTime, endDateTime);
    }
}