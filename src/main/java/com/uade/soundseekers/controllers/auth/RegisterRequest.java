package com.uade.soundseekers.controllers.auth;



import com.uade.soundseekers.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String name;
    private String lastname;
    private String password;
    private Role role;
}
