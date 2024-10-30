package com.uade.soundseekers.controllers;

import com.uade.soundseekers.entity.Event;
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
        return recommendationService.recommendEvents(userId);
    }


}
