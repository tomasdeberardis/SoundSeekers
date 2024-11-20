package com.uade.soundseekers.controllers;

import com.uade.soundseekers.dto.internals.LocalidadDTO;
import com.uade.soundseekers.service.LocalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/localidad")
public class LocalidadController {

    @Autowired
    LocalidadService localidadService;

    @GetMapping
    public ResponseEntity<List<LocalidadDTO>> getAllLocalidades() {
        return ResponseEntity.ok(localidadService.getAllLocalidades());
    }

}
