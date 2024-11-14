package com.uade.soundseekers.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class UserPreferenceProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Convert(converter = GenrePreferenceConverter.class)
    private Map<MusicGenre, Integer> genrePreferences;
    @ElementCollection
    private Set<String> likedEvents; // Set of liked event IDs
    @ElementCollection
    private Set<String> assistedEvents; // Set of attended event IDs

    public UserPreferenceProfile(Map<MusicGenre, Integer> genrePreferences, Set<String> likedEvents, Set<String> assistedEvents) {
        this.genrePreferences=genrePreferences;
        this.likedEvents=likedEvents;
        this.assistedEvents=assistedEvents;
    }

}
