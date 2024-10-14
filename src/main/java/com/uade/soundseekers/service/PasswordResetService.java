package com.uade.soundseekers.service;

import com.uade.soundseekers.entity.User;
import com.uade.soundseekers.entity.VerificationToken;
import com.uade.soundseekers.repository.UserRepository;
import com.uade.soundseekers.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public void sendPasswordResetToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        tokenRepository.save(verificationToken);
        emailService.sendPasswordResetEmail(user.getEmail(), token);
    }

    public void resetPassword(String token, String newPassword) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null || verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invalid or expired token");
        }
        User user = verificationToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}