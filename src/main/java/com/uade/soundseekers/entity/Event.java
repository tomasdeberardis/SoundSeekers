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
    private String location;

    @Setter
    @Getter
    private String genre;

    @Setter
    @Getter
    private LocalDateTime dateTime;

    @Setter
    @Getter
    private Double price;

    @Setter
    @Getter
    @OneToMany(mappedBy="event", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<Image>();

    @ManyToOne
    private User organizer;


}
