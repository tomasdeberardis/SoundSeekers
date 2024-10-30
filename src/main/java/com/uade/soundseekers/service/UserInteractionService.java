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
            EventInteraction interaction = new EventInteraction(userOpt.get(), eventOpt.get(), true, false, LocalDateTime.now());

            // Print each field to check for null values
            System.out.println("Recording Like Interaction:");
            System.out.println("User: " + interaction.getUser());
            System.out.println("Event: " + interaction.getEvent());
            System.out.println("IsLike: " + interaction.getLiked());
            System.out.println("IsAssist: " + interaction.getAssisted());
            System.out.println("Timestamp: " + interaction.getInteractionDate());

            // Save interaction
            interactionRepository.save(interaction);
        } else {
            throw new IllegalArgumentException("Invalid userId or eventId");
        }
    }

    @Transactional
    public void recordAssist(Long userId, Long eventId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Event> eventOpt = eventRepository.findById(eventId);

        if (userOpt.isPresent() && eventOpt.isPresent()) {
            EventInteraction interaction = new EventInteraction(userOpt.get(), eventOpt.get(), false, true, LocalDateTime.now());

            User user = userOpt.get();
            Event event = eventOpt.get();

            // Print each field to check for null values
            System.out.println("Recording Assist Interaction:");
            System.out.println("User: " + interaction.getUser());
            System.out.println("Event: " + interaction.getEvent());
            System.out.println("IsLike: " + interaction.getLiked());
            System.out.println("IsAssist: " + interaction.getAssisted());
            System.out.println("Timestamp: " + interaction.getInteractionDate());

            // Save interaction
            interactionRepository.save(interaction);
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
