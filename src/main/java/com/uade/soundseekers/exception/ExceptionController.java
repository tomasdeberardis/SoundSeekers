package com.uade.soundseekers.exception;

import com.uade.soundseekers.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    // Maneja NotFoundException
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFound(NotFoundException e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    // Maneja InvalidArgsException
    @ExceptionHandler(InvalidArgsException.class)
    public ResponseEntity<?> invalidArgs(InvalidArgsException e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    // Maneja BadRequestException
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequest(BadRequestException e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    // Maneja EventNotFoundException
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<?> eventNotFound(EventNotFoundException e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    // Maneja OrganizerNotFoundException
    @ExceptionHandler(OrganizerNotFoundException.class)
    public ResponseEntity<?> organizerNotFound(OrganizerNotFoundException e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    // Maneja InvalidGenreException
    @ExceptionHandler(InvalidGenreException.class)
    public ResponseEntity<?> invalidGenre(InvalidGenreException e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    // Maneja LocalidadNotFoundException
    @ExceptionHandler(LocalidadNotFoundException.class)
    public ResponseEntity<?> localidadNotFound(LocalidadNotFoundException e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage());
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }
}
