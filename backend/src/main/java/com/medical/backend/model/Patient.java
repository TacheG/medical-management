package com.medical.backend.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Patient {
    private String name;
    private String surname;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long patientID;
    private String CNP;
    private String phoneNumber;
    private String email;

    private String[] medicalHistory;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    public Patient() {}

}