package com.medical.backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class DoctorSpecialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    private BigDecimal price;
}
