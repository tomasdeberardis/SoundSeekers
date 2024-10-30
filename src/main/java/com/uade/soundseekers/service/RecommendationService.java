package com.uade.soundseekers.service;

import com.uade.soundseekers.entity.*;
import com.uade.soundseekers.repository.EventDAOImpl;
import com.uade.soundseekers.repository.EventInteractionRepository;
import com.uade.soundseekers.repository.SearchQueryRepository;
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

    // Builds user preference profile based on likes, assists, and search history
    public UserPreferenceProfile buildUserProfile(Long userId) {
        List<EventInteraction> interactions = interactionRepository.findByUserId(userId);
        List<SearchQuery> searchQueries = searchQueryRepository.findByUserId(userId);

        // Fallback: If no user data is found, return a default profile
        if (interactions.isEmpty() && searchQueries.isEmpty()) {
            return new UserPreferenceProfile(new HashMap<>(), new HashSet<>(), new HashSet<>());
        }

        Map<MusicGenre, Integer> genrePreferences = new HashMap<>();
        Set<String> likedEvents = new HashSet<>();
        Set<String> assistedEvents = new HashSet<>();

        for (EventInteraction interaction : interactions) {
            if (interaction.isLiked()) likedEvents.add(interaction.getEvent().getId().toString());
            if (interaction.isAssisted()) assistedEvents.add(interaction.getEvent().getId().toString());

            // Count genre preferences based on event genres
            for (MusicGenre genre : interaction.getEvent().getGenres()) {
                genrePreferences.put(genre, genrePreferences.getOrDefault(genre, 0) + 1);
            }
        }

        // Aggregate preferences based on searches
        for (SearchQuery search : searchQueries) {
            for (MusicGenre genre : search.getGenres()) {
                genrePreferences.put(genre, genrePreferences.getOrDefault(genre, 0) + 1);
            }
        }

        return new UserPreferenceProfile(genrePreferences, likedEvents, assistedEvents);
    }

    // Recommend events based on the user's profile
    public List<Event> recommendEvents(Long userId) {
        UserPreferenceProfile profile = buildUserProfile(userId);
        Map<MusicGenre, Integer> genrePreferences = profile.getGenrePreferences();

        // Fetch all events and filter based on preferences
        List<Event> allEvents = eventRepository.findAll();
        List<Event> recommendedEvents = allEvents.stream()
                .filter(event -> event.getGenres().stream().anyMatch(genrePreferences::containsKey))
                .sorted(Comparator.comparingInt(event -> genrePreferences.getOrDefault(event.getGenres().get(0), 0)))
                .collect(Collectors.toList());

        // If the number of recommended events is less than 5, add additional events to complete the list
        if (recommendedEvents.size() < 5) {
            Set<Long> recommendedEventIds = recommendedEvents.stream().map(Event::getId).collect(Collectors.toSet());
            List<Event> additionalEvents = allEvents.stream()
                    .filter(event -> !recommendedEventIds.contains(event.getId())) // Exclude already recommended events
                    .limit(5 - recommendedEvents.size()) // Limit to the number needed to reach 5
                    .collect(Collectors.toList());

            recommendedEvents.addAll(additionalEvents);
        }

        return recommendedEvents;
    }

}
