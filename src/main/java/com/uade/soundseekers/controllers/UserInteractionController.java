package com.uade.soundseekers.controllers;

import com.uade.soundseekers.entity.Event;
import com.uade.soundseekers.entity.EventInteraction;
import com.uade.soundseekers.service.UserInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/user-interactions")
public class UserInteractionController {

    @Autowired
    private UserInteractionService userInteractionService;

    // Endpoint for recording a like on an event
    @PostMapping("/{userId}/events/{eventId}/like")
    public ResponseEntity<String> recordLike(@PathVariable Long userId, @PathVariable Long eventId) {
        userInteractionService.recordLike(userId, eventId); // Assuming Event has a constructor with ID
        return ResponseEntity.ok("Like recorded successfully.");
    }

    @DeleteMapping("/{userId}/events/{eventId}/like")
    public ResponseEntity<String> deleteLike(@PathVariable Long userId, @PathVariable Long eventId) {
        userInteractionService.toggleLike(userId, eventId);
        return ResponseEntity.ok("Like removed successfully.");
    }
 


    // Endpoint for recording an assist on an event
    @PostMapping("/{userId}/events/{eventId}/assist")
    public ResponseEntity<String> recordAssist(@PathVariable Long userId, @PathVariable Long eventId) {
        userInteractionService.recordAssist(userId, eventId);
        return ResponseEntity.ok("Assist recorded successfully.");
    }

    // Endpoint for recording a search query
    @PostMapping("/{userId}/search")
    public ResponseEntity<String> recordSearch(
            @PathVariable Long userId,
            @RequestParam(required = false) List<String> genres,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) LocalDateTime startDateTime,
            @RequestParam(required = false) LocalDateTime endDateTime) {

        userInteractionService.recordSearch(userId, genres, minPrice, maxPrice, startDateTime, endDateTime);
        return ResponseEntity.ok("Search query recorded successfully.");
    }
}
