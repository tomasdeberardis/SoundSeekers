package com.uade.soundseekers.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventDTO {
    private Long id;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private LocalDateTime dateTime;
    private Double price;
    private Long organizerId; // Solo el ID del organizador
    private List<Long> imageIds; // Lista de IDs de imágenes, si se desea
    private List<String> genres; // Lista de géneros musicales en formato String
}