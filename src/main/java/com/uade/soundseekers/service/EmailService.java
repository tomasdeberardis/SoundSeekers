package com.uade.soundseekers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import com.uade.soundseekers.exception.BadRequestException;

@Service
public class EmailService {

    private static final String EMAIL_FROM = "wearesoundseekers@gmail.com";
    private static final String APP_PASSWORD = "znfs hqns fvgq gmyw";

    @Autowired
    private JavaMailSender mailSender;

    private Session getEmailSession() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        return Session.getInstance(prop, new jakarta.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, APP_PASSWORD);
            }
        });
    }

    public void sendVerificationEmail(String to, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(EMAIL_FROM);
            helper.setTo(to);
            helper.setSubject("Email Verification");
            helper.setText("Your verification code is: " + code, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new BadRequestException("Error al enviar el correo de verificación: " + e.getMessage());
        }
    }

    public void sendPasswordResetEmail(String to, String token) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(EMAIL_FROM);
            helper.setTo(to);
            helper.setSubject("Password Reset");
            helper.setText("Your password reset token is: " + token, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new BadRequestException("Error al enviar el correo de restablecimiento de contraseña: " + e.getMessage());
        }
    }
}
