package com.uade.soundseekers.controllers;

import com.uade.soundseekers.entity.Event;
import com.uade.soundseekers.exception.NotFoundException; // Asegúrate de importar la excepción
import com.uade.soundseekers.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/user/{userId}")
    public List<Event> recommendEventsForUser(@PathVariable Long userId) {
        // Obtener las recomendaciones de eventos para el usuario
        List<Event> events = recommendationService.recommendEvents(userId);

        // Si no se encuentran eventos recomendados para el usuario, lanzar la excepción NotFound
        if (events == null || events.isEmpty()) {
            throw new NotFoundException("No se encontraron eventos recomendados para el usuario con ID: " + userId);
        }

        // Retornar los eventos recomendados si todo está correcto
        return events;
    }
}
