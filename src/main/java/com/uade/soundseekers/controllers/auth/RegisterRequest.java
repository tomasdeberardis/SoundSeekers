package com.uade.soundseekers.controllers.auth;

import com.uade.soundseekers.entity.Role;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Name is mandatory")
    private String username;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Lastname is mandatory")
    private String lastName;

    @Min(value = 0, message = "Edad must be a positive number")
    private int edad;

    @NotBlank(message = "Password is mandatory")
    private String password;

    private Role role;
}