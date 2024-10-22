package com.uade.soundseekers.controllers.auth;

import com.uade.soundseekers.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class VerificationController {

    private final VerificationService verificationService;

    // @PostMapping("/verify")
    //  public String verifyEmail(@RequestParam("token") String token) {
        //return verificationService.verifyEmail(token);
        // }
}