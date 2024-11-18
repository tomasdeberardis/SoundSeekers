package com.uade.soundseekers.controllers.auth;

import com.uade.soundseekers.dto.AuthenticationRequest;
import com.uade.soundseekers.dto.AuthenticationResponse;
import com.uade.soundseekers.dto.MessageResponseDto;
import com.uade.soundseekers.dto.RegisterRequestDTO;
import com.uade.soundseekers.exception.InvalidArgsException; // Importar la excepción
import com.uade.soundseekers.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://front-seminario.s3-website.us-east-2.amazonaws.com/")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<MessageResponseDto> register(@Validated @RequestBody RegisterRequestDTO request) {
        if (request.getEmail() == null || request.getPassword() == null) {
            throw new InvalidArgsException("Datos de registro inválidos: falta el correo electrónico o la contraseña");
        }
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        if (request.getEmail() == null || request.getPassword() == null) {
            throw new InvalidArgsException("Datos de autenticación inválidos: falta el correo electrónico o la contraseña");
        }
        return ResponseEntity.ok(service.authenticate(request));
    }
}
