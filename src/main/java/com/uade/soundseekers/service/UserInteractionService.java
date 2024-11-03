package com.uade.soundseekers.service;

import com.uade.soundseekers.entity.*;
import com.uade.soundseekers.repository.EventDAOImpl;
import com.uade.soundseekers.repository.EventInteractionRepository;
import com.uade.soundseekers.repository.SearchQueryRepository;
import com.uade.soundseekers.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserInteractionService {

    @Autowired
    private EventInteractionRepository interactionRepository;

    @Autowired
    private SearchQueryRepository searchQueryRepository;

    @Autowired
    private EventDAOImpl eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void recordLike(Long userId, Long eventId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Event> eventOpt = eventRepository.findById(eventId);
    
        if (userOpt.isPresent() && eventOpt.isPresent()) {
            User user = userOpt.get();
            Event event = eventOpt.get();
    
            // Buscar la interacción existente
            Optional<EventInteraction> existingInteraction = interactionRepository.findByUserAndEvent(user, event);
    
            if (existingInteraction.isPresent()) {
                // Si existe, actualizar el estado de "like"
                EventInteraction interaction = existingInteraction.get();
                interaction.setLiked(!interaction.getLiked()); // Cambiar el estado de like
                interaction.setAssisted(false); // Puedes cambiar esto si es necesario
                interactionRepository.save(interaction);
            } else {
                // Si no existe, crear una nueva interacción
                EventInteraction newInteraction = new EventInteraction(user, event, true, false, LocalDateTime.now());
                interactionRepository.save(newInteraction);
            }
        } else {
            throw new IllegalArgumentException("Invalid userId or eventId");
        }
    }
    

    @Transactional
    public void toggleLike(Long userId, Long eventId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Event> eventOpt = eventRepository.findById(eventId);
    
        if (userOpt.isPresent() && eventOpt.isPresent()) {
            User user = userOpt.get();
            Event event = eventOpt.get();
            
            // Buscar la interacción existente
            Optional<EventInteraction> interactionOpt = interactionRepository.findByUserAndEvent(user, event);
            
            if (interactionOpt.isPresent()) {
                EventInteraction interaction = interactionOpt.get();
                // Cambiar el estado de like
                interaction.setLiked(false); // Cambiar a 0
                interactionRepository.save(interaction);
            } else {
                throw new IllegalArgumentException("No interaction found for the given user and event.");
            }
        } else {
            throw new IllegalArgumentException("Invalid userId or eventId");
        }
    }
    

    @Transactional
public void recordAssist(Long userId, Long eventId) {
    Optional<User> userOpt = userRepository.findById(userId);
    Optional<Event> eventOpt = eventRepository.findById(eventId);

    if (userOpt.isPresent() && eventOpt.isPresent()) {
        User user = userOpt.get();
        Event event = eventOpt.get();

        // Verificar si ya existe una interacción para el usuario y el evento
        Optional<EventInteraction> interactionOpt = interactionRepository.findByUserAndEvent(user, event);

        if (interactionOpt.isPresent()) {
            // Si existe, puedes decidir qué hacer. Aquí podrías optar por actualizar el estado de asistencia.
            EventInteraction interaction = interactionOpt.get();
            interaction.setAssisted(true); // Marcar como asistido
            interactionRepository.save(interaction);
        } else {
            // Si no existe, crear una nueva interacción
            EventInteraction newInteraction = new EventInteraction(user, event, false, true, LocalDateTime.now());
            interactionRepository.save(newInteraction);
        }

        // Agregar al usuario en la lista de asistentes del evento
        event.getAttendees().add(user);
        eventRepository.save(event);
    } else {
        throw new IllegalArgumentException("Invalid userId or eventId");
    }
}

    
    
    public void recordSearch(Long userId, List<String> genreStrings, Double minPrice, Double maxPrice, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            List<MusicGenre> genres = genreStrings != null
                    ? genreStrings.stream().map(MusicGenre::valueOf).collect(Collectors.toList())
                    : null;

            SearchQuery searchQuery = new SearchQuery(user, genres, minPrice, maxPrice, startDateTime, endDateTime, LocalDateTime.now());
            searchQueryRepository.save(searchQuery);
        } else {
            throw new IllegalArgumentException("Invalid userId");
        }
    }
}
