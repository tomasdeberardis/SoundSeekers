package com.uade.soundseekers.service;

import com.uade.soundseekers.entity.*;
import com.uade.soundseekers.repository.EventDAOImpl;
import com.uade.soundseekers.repository.EventInteractionRepository;
import com.uade.soundseekers.repository.SearchQueryRepository;
import com.uade.soundseekers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private EventInteractionRepository interactionRepository;

    @Autowired
    private SearchQueryRepository searchQueryRepository;

    @Autowired
    private EventDAOImpl eventRepository;

    @Autowired
    private UserRepository userRepository;

    // Builds user preference profile based on likes, assists, search history, localidad, and preferred genres
    public UserPreferenceProfile buildUserProfile(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        List<EventInteraction> interactions = interactionRepository.findByUserId(userId);
        List<SearchQuery> searchQueries = searchQueryRepository.findByUserId(userId);

        if (userOpt.isEmpty() || (interactions.isEmpty() && searchQueries.isEmpty())) {
            return new UserPreferenceProfile(new HashMap<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        }

        User user = userOpt.get();
        Map<MusicGenre, Integer> genrePreferences = new HashMap<>();
        Set<String> likedEvents = new HashSet<>();
        Set<String> assistedEvents = new HashSet<>();
        Set<Long> preferredLocalidades = new HashSet<>();

        // Include the user's primary localidad as a preferred localidad
        if (user.getLocalidad() != null) {
            preferredLocalidades.add(user.getLocalidad().getId());
        }

        // Add preferred genres from the user's profile with a base weight
        for (MusicGenre preferredGenre : user.getGenerosMusicalesPreferidos()) {
            genrePreferences.put(preferredGenre, genrePreferences.getOrDefault(preferredGenre, 0) + 2); // Weight for user preferences
        }

        // Process interactions for genre and localidad preferences
        for (EventInteraction interaction : interactions) {
            if (interaction.isLiked()) likedEvents.add(interaction.getEvent().getId().toString());
            if (interaction.isAssisted()) assistedEvents.add(interaction.getEvent().getId().toString());

            for (MusicGenre genre : interaction.getEvent().getGenres()) {
                genrePreferences.put(genre, genrePreferences.getOrDefault(genre, 0) + 1);
            }

            if (interaction.getEvent().getLocalidad() != null) {
                preferredLocalidades.add(interaction.getEvent().getLocalidad().getId());
            }
        }

        // Process search queries for genre and localidad preferences
        for (SearchQuery search : searchQueries) {
            for (MusicGenre genre : search.getGenres()) {
                genrePreferences.put(genre, genrePreferences.getOrDefault(genre, 0) + 1);
            }
            if (search.getLocalidadId() != null) {
                preferredLocalidades.add(search.getLocalidadId());
            }
        }

        return new UserPreferenceProfile(genrePreferences, likedEvents, assistedEvents, preferredLocalidades);
    }

    // Recommend events based on user's profile, including localidad and preferred genres
    public List<Event> recommendEvents(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UserPreferenceProfile profile = buildUserProfile(userId);

        // Combine interaction-based preferences with static user preferences
        Map<MusicGenre, Integer> genrePreferences = new HashMap<>(profile.getGenrePreferences());
        user.getGenerosMusicalesPreferidos().forEach(genre ->
                genrePreferences.putIfAbsent(genre, 1) // Static preference with a lower score if no interactions exist
        );

        Set<Long> preferredLocalidades = new HashSet<>(profile.getPreferredLocalidades());
        if (preferredLocalidades.isEmpty() && user.getLocalidad() != null) {
            preferredLocalidades.add(user.getLocalidad().getId());
        }

        List<Event> allEvents = eventRepository.findAll();
        List<Event> recommendedEvents = allEvents.stream()
                .filter(event -> event.getGenres().stream().anyMatch(genrePreferences::containsKey)
                        || (event.getLocalidad() != null && preferredLocalidades.contains(event.getLocalidad().getId())))
                .sorted((e1, e2) -> {
                    int genreScore1 = genrePreferences.getOrDefault(e1.getGenres().get(0), 0);
                    int genreScore2 = genrePreferences.getOrDefault(e2.getGenres().get(0), 0);

                    boolean localidadPreference1 = e1.getLocalidad() != null && preferredLocalidades.contains(e1.getLocalidad().getId());
                    boolean localidadPreference2 = e2.getLocalidad() != null && preferredLocalidades.contains(e2.getLocalidad().getId());

                    // Prioritize by localidad preference, then by genre score
                    if (localidadPreference1 && !localidadPreference2) return -1;
                    if (!localidadPreference1 && localidadPreference2) return 1;
                    return Integer.compare(genreScore2, genreScore1);
                })
                .collect(Collectors.toList());

        if (recommendedEvents.size() < 5) {
            Set<Long> recommendedEventIds = recommendedEvents.stream().map(Event::getId).collect(Collectors.toSet());
            List<Event> additionalEvents = allEvents.stream()
                    .filter(event -> !recommendedEventIds.contains(event.getId()))
                    .limit(5 - recommendedEvents.size())
                    .toList();
            recommendedEvents.addAll(additionalEvents);
        }

        return recommendedEvents;
    }
}
