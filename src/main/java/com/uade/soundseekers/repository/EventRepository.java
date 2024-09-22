package com.uade.soundseekers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uade.soundseekers.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Buscar eventos por ubicación y género musical
    List<Event> findByLocationAndGenre(String location, String genre);

    // Buscar eventos por rango de fechas
    List<Event> findByDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    // Buscar eventos organizados por un usuario
    List<Event> findByOrganizerId(Long organizerId);

    // Buscar eventos por precio máximo
    List<Event> findByPriceLessThanEqual(Double price);

    // Buscar eventos por género y rango de fechas
    List<Event> findByGenreAndDateTimeBetween(String genre, LocalDateTime startDateTime, LocalDateTime endDateTime);

    // Buscar eventos por ubicación, género y rango de fechas
    List<Event> findByLocationAndGenreAndDateTimeBetween(String location, String genre, LocalDateTime startDateTime, LocalDateTime endDateTime);
}