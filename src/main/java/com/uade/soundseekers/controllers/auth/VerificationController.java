package com.uade.soundseekers.controllers.auth;

import com.uade.soundseekers.dto.MessageResponseDto;
import com.uade.soundseekers.exception.NotFoundException;
import com.uade.soundseekers.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://front-seminario.s3-website.us-east-2.amazonaws.com/")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    @PostMapping("/verify")
    public MessageResponseDto verifyEmail(@RequestParam("token") String token, @RequestParam("email") String email) {
        // Llamada al servicio para verificar el email y el token
        MessageResponseDto response = verificationService.verifyEmail(token, email);

        // Si no se encuentra el token o el email no es válido, lanzamos una excepción NotFound
        if (response == null) {
            throw new NotFoundException("Email o token de verificación no válido.");
        }

        return response;
    }

    @PostMapping("/resend-verification")
    public MessageResponseDto resendVerification(@RequestParam("email") String email) {
        // Llamada al servicio para reenviar el correo de verificación
        MessageResponseDto response = verificationService.resendVerification(email);

        // Si no se encuentra el email, lanzamos una excepción NotFound
        if (response == null) {
            throw new NotFoundException("No se pudo reenviar el correo de verificación. Email no encontrado.");
        }

        return response;
    }
}
