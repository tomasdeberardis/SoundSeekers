package com.uade.soundseekers.controllers.auth;

import com.uade.soundseekers.dto.MessageResponseDto;
import com.uade.soundseekers.dto.PasswordResetRequestDto;
import com.uade.soundseekers.exception.InvalidArgsException;
import com.uade.soundseekers.exception.NotFoundException;
import com.uade.soundseekers.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://front-seminario.s3-website.us-east-2.amazonaws.com/")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponseDto> forgotPassword(@RequestParam("email") String email) {
        // Validación de la entrada antes de hacer la llamada al servicio
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidArgsException("El correo electrónico no puede estar vacío");
        }

        // Llamada al servicio para enviar el token de restablecimiento
        MessageResponseDto response = passwordResetService.sendPasswordResetToken(email);

        // Si no se pudo generar la respuesta, lanzamos una excepción NotFound
        if (response == null) {
            throw new NotFoundException("No se pudo enviar el token de restablecimiento de contraseña. Verifique el correo electrónico ingresado.");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponseDto> resetPassword(@RequestBody PasswordResetRequestDto request) {
        // Validación de la entrada antes de hacer la llamada al servicio
        if (request == null || request.getEmail() == null || request.getPassword() == null) {
            throw new InvalidArgsException("Datos de restablecimiento de contraseña inválidos: falta el correo electrónico o la nueva contraseña");
        }

        // Llamada al servicio para restablecer la contraseña
        MessageResponseDto response = passwordResetService.resetPassword(request);

        // Si no se pudo generar la respuesta, lanzamos una excepción NotFound
        if (response == null) {
            throw new NotFoundException("No se pudo restablecer la contraseña. Verifique los datos ingresados.");
        }

        return ResponseEntity.ok(response);
    }
}
