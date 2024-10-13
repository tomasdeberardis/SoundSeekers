package com.uade.soundseekers.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    @Getter
    private Long id;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String description;

    @Setter
    @Getter
    private Double latitude;

    @Setter
    @Getter
    private Double longitude;

    @Setter
    @Getter
    private LocalDateTime dateTime;

    @Setter
    @Getter
    private Double price;

    @Setter
    @Getter
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    @Setter
    @Getter
    @ManyToMany
    @JoinTable(
            name = "event_users",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> attendees = new ArrayList<>();

    @Setter
    @Getter
    @ElementCollection(targetClass = musicGenre.class)
    @Enumerated(EnumType.STRING)
    private List<musicGenre> genres = new ArrayList<>();

    @Setter
    @Getter
    @ManyToOne
    private User organizer;
}