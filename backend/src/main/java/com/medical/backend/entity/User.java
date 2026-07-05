package com.medical.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    @Enumerated(EnumType.STRING)
    private AuthRole role = AuthRole.User;

    private boolean isDoctor = false; // User-ul e Patient sau Doctor
    private boolean doctorRequest = false;  // false = nu a cerut aprobare la admin
                                            // true = a trimis cererea catre admin

    public User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
