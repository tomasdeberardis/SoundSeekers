package com.uade.soundseekers.service;

import com.uade.soundseekers.entity.Localidad;
import com.uade.soundseekers.repository.LocalidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalidadService {

    @Autowired
    private LocalidadRepository localidadRepository;

    public List<Localidad> getAllLocalidades() {
        return localidadRepository.findAll();
    }

    public void saveAll(List<Localidad> localidades) {
        localidadRepository.saveAll(localidades);
    }
}