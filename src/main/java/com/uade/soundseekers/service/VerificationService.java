package com.uade.soundseekers.service;

import com.uade.soundseekers.dto.MessageResponseDto;
import com.uade.soundseekers.entity.User;
import com.uade.soundseekers.entity.VerificationToken;
import com.uade.soundseekers.exception.BadRequestException;
import com.uade.soundseekers.exception.NotFoundException;
import com.uade.soundseekers.repository.UserRepository;
import com.uade.soundseekers.repository.VerificationTokenRepository;
import jakarta.mail.MessagingException;
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

    public MessageResponseDto verifyEmail(String token, String email) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (!verificationToken.getUser().getEmail().equals(email) || verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Invalid token");
        }
        User user = verificationToken.getUser();
        user.setEmailVerified(true);
        userRepository.save(user);
        return new MessageResponseDto("Email verified successfully");
    }

    public MessageResponseDto resendVerification(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        User user = optionalUser.get();
        if (user.isEmailVerified()) {
            throw new BadRequestException("Email already verified");
        }
        VerificationToken existingToken = tokenRepository.findByUser(user);
        tokenRepository.delete(existingToken);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        tokenRepository.save(verificationToken);
        try {
            emailSender.sendVerificationEmail(user.getEmail(), token);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send verification email", e);
        }
        return new MessageResponseDto("Verification email sent successfully");
    }
}