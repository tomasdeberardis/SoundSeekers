package com.uade.soundseekers.controllers;

import com.uade.soundseekers.dto.MessageResponseDto;
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
    public ResponseEntity<MessageResponseDto> recordLike(@PathVariable Long userId, @PathVariable Long eventId) {
        return ResponseEntity.ok(userInteractionService.recordLike(userId, eventId));
    }

    @DeleteMapping("/{userId}/events/{eventId}/like")
    public ResponseEntity<MessageResponseDto> deleteLike(@PathVariable Long userId, @PathVariable Long eventId) {
        return ResponseEntity.ok(userInteractionService.toggleLike(userId, eventId));
    }

    // Endpoint for recording an assist on an event
    @PostMapping("/{userId}/events/{eventId}/assist")
    public ResponseEntity<MessageResponseDto> recordAssist(@PathVariable Long userId, @PathVariable Long eventId) {
        return ResponseEntity.ok(userInteractionService.recordAssist(userId, eventId));
    }

    // Endpoint for recording a search query
    @PostMapping("/{userId}/search")
    public ResponseEntity<MessageResponseDto> recordSearch(
            @PathVariable Long userId,
            @RequestParam(required = false) List<String> genres,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) LocalDateTime startDateTime,
            @RequestParam(required = false) LocalDateTime endDateTime) {
        return ResponseEntity.ok(userInteractionService.recordSearch(userId, genres, minPrice, maxPrice, startDateTime, endDateTime));
    }
}
