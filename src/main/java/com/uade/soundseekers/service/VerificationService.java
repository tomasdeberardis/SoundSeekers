package com.uade.soundseekers.service;

import com.uade.soundseekers.entity.User;
import com.uade.soundseekers.entity.VerificationToken;
import com.uade.soundseekers.repository.UserRepository;
import com.uade.soundseekers.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;

    public String verifyEmail(String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return "Invalid token";
        }

        User user = verificationToken.getUser();
        user.setEmailVerified(true);
        userRepository.save(user);

        return "Email verified successfully";
    }
}