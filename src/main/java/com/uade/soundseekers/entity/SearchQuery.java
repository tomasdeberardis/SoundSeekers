package com.uade.soundseekers.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class SearchQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private List<MusicGenre> genres;
    private Double minPrice;
    private Double maxPrice;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private LocalDateTime searchDate;

    public SearchQuery(User user, List<MusicGenre> genres, Double minPrice, Double maxPrice, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime now) {
    }

    // Constructors, getters, setters
}