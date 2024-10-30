package com.uade.soundseekers.repository;

import com.uade.soundseekers.entity.EventInteraction;
import com.uade.soundseekers.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventInteractionRepository extends JpaRepository<EventInteraction, Long> {
    List<EventInteraction> findByUser(User user);
}
