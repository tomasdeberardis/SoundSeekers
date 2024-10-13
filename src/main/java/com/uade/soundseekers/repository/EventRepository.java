package com.uade.soundseekers.repository;

import com.uade.soundseekers.entity.Event;
import com.uade.soundseekers.entity.musicGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Filtros avanzados para buscar eventos
    @Query("SELECT e FROM Event e WHERE "
            + "(:name IS NULL OR e.name LIKE %:name%) AND "
            + "(:genre IS NULL OR e.genre = :genre) AND "
            + "(:startDate IS NULL OR e.dateTime >= :startDate) AND "
            + "(:endDate IS NULL OR e.dateTime <= :endDate) AND "
            + "(:minPrice IS NULL OR e.price >= :minPrice) AND "
            + "(:maxPrice IS NULL OR e.price <= :maxPrice)")
    List<Event> findByAdvancedFilters(@Param("name") String name,
                                      @Param("genre") String genre,
                                      @Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate,
                                      @Param("minPrice") Double minPrice,
                                      @Param("maxPrice") Double maxPrice);

    // Búsqueda de eventos por proximidad
    @Query("SELECT e FROM Event e WHERE "
            + "(6371 * acos(cos(radians(:lat)) * cos(radians(e.latitude)) "
            + "* cos(radians(e.longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(e.latitude)))) <= :radius")
    List<Event> findByProximity(@Param("lat") Double lat, @Param("lng") Double lng, @Param("radius") Double radius);
}