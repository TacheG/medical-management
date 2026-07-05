package com.medical.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    private String email;

    @Enumerated(EnumType.String)
    private AuthRole role = AuthRole.User;
    private boolean isDoctor = false; // User-ul e Patient sau Doctor
    private boolean doctorRequest = false;  // false = nu a cerut aprobare la admin
                                            // true = a trimis cererea catre admin

    public User() {}

    public User(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
