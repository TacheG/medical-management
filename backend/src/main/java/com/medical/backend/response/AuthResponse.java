package com.medical.backend.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String token;
    private String role;

    public AuthResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }
}
