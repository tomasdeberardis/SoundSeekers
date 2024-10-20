package com.uade.soundseekers.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double latitude;

    private Double longitude;

    private LocalDateTime dateTime;

    private Double price;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "event_users", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> attendees = new ArrayList<>();

    @ElementCollection(targetClass = MusicGenre.class)
    @Enumerated(EnumType.STRING)
    private List<MusicGenre> genres = new ArrayList<>();

    @ManyToOne
    private User organizer;

    @ManyToOne
    @JoinColumn(name = "localidad_id", nullable = false)
    private Localidad localidad;
}