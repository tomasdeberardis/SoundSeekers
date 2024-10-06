package com.uade.soundseekers.controllers.auth;

import com.uade.soundseekers.entity.User;
import com.uade.soundseekers.entity.VerificationToken;
import com.uade.soundseekers.repository.UserRepository;
import com.uade.soundseekers.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class VerificationController {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token) {
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