package com.medical.backend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Doctor {
    private String name;
    private String surname;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;

    private String[] specialization;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    public Doctor() {}
}