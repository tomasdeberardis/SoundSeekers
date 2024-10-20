package com.uade.soundseekers.service;

import com.uade.soundseekers.controllers.auth.AuthenticationRequest;
import com.uade.soundseekers.controllers.auth.AuthenticationResponse;
import com.uade.soundseekers.controllers.auth.RegisterRequest;
import com.uade.soundseekers.controllers.config.JwtService;
import com.uade.soundseekers.entity.User;
import com.uade.soundseekers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private static final String EMAIL_REGEX = "^(?!.*\\.\\..)(?!.*\\.$)(?!^\\.)[A-Za-z0-9][A-Za-z0-9._%+-]*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$"; //restricciones del mail

    public AuthenticationResponse register(RegisterRequest request) {
        if (!isValidEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email proporcionado no es válido.");
        }

        var user = User.builder().name(request.getName()).username(request.getUsername()).edad(request.getEdad()).lastName(request.getLastName()).email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(request.getRole()).generosMusicalesPreferidos(request.getGenerosMusicalesPreferidos()).build();

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().accessToken(jwtToken).role(user.getRole().name()).build();
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
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