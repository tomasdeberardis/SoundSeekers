package com.uade.soundseekers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SearchQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ElementCollection
    private List<MusicGenre> genres;
    private Double minPrice;
    private Double maxPrice;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private LocalDateTime searchDate;
}