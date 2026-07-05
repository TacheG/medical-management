package com.medical.backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Patient extends User {
    private String CNP;
    private String phoneNumber;

    private String[] medicalHistory;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    public Patient() {}

}