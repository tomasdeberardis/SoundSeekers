package com.uade.soundseekers.controllers.auth;

import com.uade.soundseekers.entity.MusicGenre;
import com.uade.soundseekers.entity.Role;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@CrossOrigin(origins = "http://front-seminario.s3-website.us-east-2.amazonaws.com/")
public class RegisterRequest {

    @NotBlank(message = "El Email es obligatorio")
    private String email;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;

    @Min(value = 0, message = "La edad debe ser mayor a 0")
    private int edad;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    private Role role;
    private List<MusicGenre> generosMusicalesPreferidos;
    private Long localidadId;

    public RegisterRequest() {
        this.generosMusicalesPreferidos = new ArrayList<>(); // Inicializa como lista vacía
    }
}