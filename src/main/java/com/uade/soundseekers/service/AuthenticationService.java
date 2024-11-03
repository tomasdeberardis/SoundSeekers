package com.uade.soundseekers.service;

import com.uade.soundseekers.dto.AuthenticationRequest;
import com.uade.soundseekers.dto.AuthenticationResponse;
import com.uade.soundseekers.controllers.auth.RegisterRequest;
import com.uade.soundseekers.controllers.config.JwtService;
import com.uade.soundseekers.entity.Localidad;
import com.uade.soundseekers.entity.MusicGenre;
import com.uade.soundseekers.entity.User;
import com.uade.soundseekers.entity.VerificationToken;
import com.uade.soundseekers.exception.BadRequestException;
import com.uade.soundseekers.repository.LocalidadRepository;
import com.uade.soundseekers.repository.UserRepository;
import com.uade.soundseekers.repository.VerificationTokenRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailSender;
    private final LocalidadRepository localidadRepository;

    private static final String EMAIL_REGEX = "^(?!.*\\.\\..)(?!.*\\.$)(?!^\\.)[A-Za-z0-9][A-Za-z0-9._%+-]*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$"; //restricciones del mail
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$"; // Minimum 8 characters, one uppercase, one lowercase, one number

    public AuthenticationResponse register(RegisterRequest request) {
        if (!isValidEmail(request.getEmail())) {
            throw new BadRequestException("El email proporcionado no es válido.");
        }

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("El email ya está registrado.");
        }

        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new BadRequestException("El nombre de usuario ya está registrado.");
        }

        if (!isValidPassword(request.getPassword())) {
            throw new BadRequestException("La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número.");
        }

        User user = User.builder()
            .email(request.getEmail())
            .name(request.getName())
            .password(passwordEncoder.encode(request.getPassword()))
            .lastName(request.getLastName())
            .username(request.getUsername())
            .edad(request.getEdad())
            .isEmailVerified(false)
            .role(request.getRole())
            .build();

        Localidad localidad = localidadRepository.findById(request.getLocalidadId())
            .orElseThrow(() -> new RuntimeException("Localidad not found with ID: " + request.getLocalidadId()));
        user.setLocalidad(localidad);

        List<MusicGenre> generosMusicales = request.getGenerosMusicalesPreferidos().stream()
            .map(genre -> {
                try {
                    return MusicGenre.valueOf(genre.name().toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid genre: " + genre);
                }
            })
            .collect(Collectors.toList());
        user.setGenerosMusicalesPreferidos(generosMusicales);

        repository.save(user);

        // Generar y guardar el token de verificación
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        tokenRepository.save(verificationToken);

        // Enviar el email de verificación
        try {
            emailSender.sendVerificationEmail(user.getEmail(), token);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send verification email", e);
        }

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .role(user.getRole().name())
            .userId(user.getId())
            .build();
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        return pattern.matcher(password).matches();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .role(user.getRole().name())
            .userId(user.getId())
            .build();
    }
}