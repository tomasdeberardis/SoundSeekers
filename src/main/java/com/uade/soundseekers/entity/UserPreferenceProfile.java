package com.uade.soundseekers.entity;
import com.uade.soundseekers.entity.MusicGenre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Setter
@Getter
@Entity
public class UserPreferenceProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Convert(converter = GenrePreferenceConverter.class)
    private Map<MusicGenre, Integer> genrePreferences;
    private Set<String> likedEvents; // Set of liked event IDs
    private Set<String> assistedEvents; // Set of attended event IDs

    public UserPreferenceProfile(Map<MusicGenre, Integer> genrePreferences, Set<String> likedEvents, Set<String> assistedEvents) {

        this.genrePreferences=genrePreferences;
        this.likedEvents=likedEvents;
        this.assistedEvents=assistedEvents;
    }

    // Constructor, getters, and setters
}
