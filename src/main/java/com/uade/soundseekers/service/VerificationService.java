package com.uade.soundseekers.service;

import com.uade.soundseekers.dto.MessageResponseDto;
import com.uade.soundseekers.entity.User;
import com.uade.soundseekers.entity.VerificationToken;
import com.uade.soundseekers.exception.BadRequestException;
import com.uade.soundseekers.exception.NotFoundException;
import com.uade.soundseekers.repository.UserRepository;
import com.uade.soundseekers.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailSender;

    // Método para verificar el email
    public MessageResponseDto verifyEmail(String token, String email) {
        // Buscar al usuario por email
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("No existe un usuario con ese email");
        }

        User user = optionalUser.get();

        // Verificar si el usuario ya ha verificado su email
        if (user.isEmailVerified()) {
            throw new BadRequestException("La cuenta ya fue verificada");
        }

        // Buscar el token de verificación asociado al usuario
        VerificationToken verificationToken = tokenRepository.findByUser(user);
        if (verificationToken == null) {
            throw new NotFoundException("Token de verificación no encontrado");
        }

        // Validar si el token coincide y si no ha expirado
        if (!verificationToken.getToken().equals(token) || verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Token inválido o expirado");
        }

        // Marcar el usuario como verificado
        user.setEmailVerified(true);
        userRepository.save(user);
        return new MessageResponseDto("Email verificado exitosamente");
    }

    // Método para reenviar el correo de verificación
    public MessageResponseDto resendVerification(String email) {
        // Buscar al usuario por email
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("No existe un usuario con ese email");
        }

        User user = optionalUser.get();

        // Verificar si el usuario ya ha verificado su email
        if (user.isEmailVerified()) {
            throw new BadRequestException("La cuenta ya fue verificada");
        }

        // Eliminar cualquier token de verificación existente
        VerificationToken existingToken = tokenRepository.findByUser(user);
        if (existingToken != null) {
            tokenRepository.delete(existingToken);
        }

        // Crear un nuevo token de verificación
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        tokenRepository.save(verificationToken);

        // Enviar el correo de verificación
        try {
            emailSender.sendVerificationEmail(user.getEmail(), token);
        } catch (BadRequestException e) {
            // Capturamos la BadRequestException que es lanzada en EmailService
            throw new RuntimeException("Error al enviar el correo de verificación: " + e.getMessage(), e);
        }

        return new MessageResponseDto("Correo de verificación enviado exitosamente.");
    }
}
