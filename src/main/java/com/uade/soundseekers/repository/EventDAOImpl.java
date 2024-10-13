package com.uade.soundseekers.repository;

import com.uade.soundseekers.entity.Event;
import com.uade.soundseekers.entity.EventDAO;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class EventDAOImpl implements EventDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Event> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Event.class, id));
    }

    @Override
    public List<Event> findAll() {
        TypedQuery<Event> query = entityManager.createQuery("SELECT e FROM Event e", Event.class);
        return query.getResultList();
    }

    @Override
    public void save(Event event) {
        entityManager.persist(event);
    }

    @Override
    public void update(Event event) {
        entityManager.merge(event);
    }

    @Override
    public void deleteById(Long id) {
        Event event = entityManager.find(Event.class, id);
        if (event != null) {
            entityManager.remove(event);
        }
    }

    @Override
    public List<Event> findByAdvancedFilters(String name, String genre, LocalDateTime startDate, LocalDateTime endDate, Double minPrice, Double maxPrice) {
        StringBuilder queryStr = new StringBuilder("SELECT e FROM Event e WHERE 1=1");

        if (name != null && !name.isEmpty()) queryStr.append(" AND e.name LIKE :name");
        if (genre != null && !genre.isEmpty()) queryStr.append(" AND :genre MEMBER OF e.genres");
        if (startDate != null) queryStr.append(" AND e.dateTime >= :startDate");
        if (endDate != null) queryStr.append(" AND e.dateTime <= :endDate");
        if (minPrice != null) queryStr.append(" AND e.price >= :minPrice");
        if (maxPrice != null) queryStr.append(" AND e.price <= :maxPrice");

        TypedQuery<Event> query = entityManager.createQuery(queryStr.toString(), Event.class);

        if (name != null && !name.isEmpty()) query.setParameter("name", "%" + name + "%");
        if (genre != null && !genre.isEmpty()) query.setParameter("genre", genre);
        if (startDate != null) query.setParameter("startDate", startDate);
        if (endDate != null) query.setParameter("endDate", endDate);
        if (minPrice != null) query.setParameter("minPrice", minPrice);
        if (maxPrice != null) query.setParameter("maxPrice", maxPrice);

        return query.getResultList();
    }

    @Override
    public List<Event> findByProximity(Double lat, Double lng, Double radius) {
        String queryStr = "SELECT e FROM Event e WHERE (6371 * acos(cos(radians(:lat)) * cos(radians(e.latitude)) * cos(radians(e.longitude) - radians(:lng)) + sin(radians(:lat)) * sin(radians(e.latitude)))) <= :radius";

        TypedQuery<Event> query = entityManager.createQuery(queryStr, Event.class);
        query.setParameter("lat", lat);
        query.setParameter("lng", lng);
        query.setParameter("radius", radius);

        return query.getResultList();
    }
}
