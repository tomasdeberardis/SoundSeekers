package com.uade.soundseekers.repository;

import com.uade.soundseekers.entity.Event;
import com.uade.soundseekers.entity.EventDAO;
import com.uade.soundseekers.entity.musicGenre;
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

    public List<Event> findByAdvancedFilters(String name,List<musicGenre> genres, LocalDateTime startDate, LocalDateTime endDate, Double minPrice, Double maxPrice) {
        StringBuilder queryBuilder = new StringBuilder("SELECT e FROM Event e WHERE 1=1 ");

        if (name != null) {
            queryBuilder.append("AND e.name LIKE :name ");
        }
        if (startDate != null) {
            queryBuilder.append("AND e.dateTime >= :startDate ");
        }
        if (endDate != null) {
            queryBuilder.append("AND e.dateTime <= :endDate ");
        }
        if (minPrice != null) {
            queryBuilder.append("AND e.price >= :minPrice ");
        }
        if (maxPrice != null) {
            queryBuilder.append("AND e.price <= :maxPrice ");
        }
        if (genres != null && !genres.isEmpty()) {
            queryBuilder.append("AND (");
            for (int i = 0; i < genres.size(); i++) {
                queryBuilder.append(":genre").append(i).append(" MEMBER OF e.genres");
                if (i < genres.size() - 1) {
                    queryBuilder.append(" OR ");
                }
            }
            queryBuilder.append(") ");
        }

        TypedQuery<Event> query = entityManager.createQuery(queryBuilder.toString(), Event.class);

        if (name != null) {
            query.setParameter("name", "%" + name + "%");
        }
        if (startDate != null) {
            query.setParameter("startDate", startDate);
        }
        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }
        if (minPrice != null) {
            query.setParameter("minPrice", minPrice);
        }
        if (maxPrice != null) {
            query.setParameter("maxPrice", maxPrice);
        }
        if (genres != null && !genres.isEmpty()) {
            for (int i = 0; i < genres.size(); i++) {
                query.setParameter("genre" + i, genres.get(i));
            }
        }

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
