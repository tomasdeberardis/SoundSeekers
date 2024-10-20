package com.uade.soundseekers.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class EventDTO {
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private LocalDateTime dateTime;
    private Double price;
    private List<String> genres;
    private Long organizerId; // Solo el ID del organizador
    private List<Long> imageIds; // Lista de IDs de imágenes, si se desea
    private Long localidadId;
}