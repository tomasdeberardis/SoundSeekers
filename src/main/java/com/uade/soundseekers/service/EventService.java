package com.uade.soundseekers.service;

import com.uade.soundseekers.entity.Event;
import com.uade.soundseekers.entity.EventDAO;
import com.uade.soundseekers.repository.EventRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventDAO eventDAO;

    // Obtener todos los eventos
    public List<Event> getAllEvents() {
        return eventDAO.findAll();
    }

    // Crear un nuevo evento
    public Event createEvent(Event event) {
        eventDAO.save(event);
        return event;
    }

    // Editar un evento existente
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
    public void deleteEvent(Long id) {
        eventDAO.deleteById(id);
    }

    // Búsqueda de eventos por filtros avanzados
    public List<Event> findByAdvancedFilters(String name, String genre, LocalDateTime startDate, LocalDateTime endDate, Double minPrice, Double maxPrice) {
        return eventDAO.findByAdvancedFilters(name, genre, startDate, endDate, minPrice, maxPrice);
    }

    // Búsqueda de eventos por proximidad (latitud, longitud, radio)
    public List<Event> searchEventsByProximity(Double lat, Double lng, Double radius) {
        return eventDAO.findByProximity(lat, lng, radius);
    }
}
