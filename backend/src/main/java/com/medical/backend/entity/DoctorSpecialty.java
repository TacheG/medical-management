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

    @Enumerated(EnumType.STRING)
    private SpecialtyType specialtyType;

    private BigDecimal price;
}
