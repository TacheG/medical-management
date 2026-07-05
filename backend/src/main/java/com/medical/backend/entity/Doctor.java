package com.medical.backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Doctor extends User {

    private String[] specialization;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

}