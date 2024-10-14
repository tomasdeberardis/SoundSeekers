package com.uade.soundseekers.controllers.auth;

import com.uade.soundseekers.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam("email") String email) {
        passwordResetService.sendPasswordResetToken(email);
        return "Password reset token sent to email";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword) {
        passwordResetService.resetPassword(token, newPassword);
        return "Password reset successfully";
    }
}