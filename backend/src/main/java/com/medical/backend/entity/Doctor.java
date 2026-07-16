package com.medical.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    private String licenseNumber;

    @Enumerated(EnumType.STRING)
    private DoctorStatus status = DoctorStatus.PENDING;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @OneToMany (mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<DoctorSpecialty> doctorSpecialties = new ArrayList<>();

    @Column(length = 2000)
    private String biography;

    private Integer experienceYears;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DoctorSchedule> schedules = new ArrayList<>();
}