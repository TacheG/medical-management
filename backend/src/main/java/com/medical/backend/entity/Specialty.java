package com.medical.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany (mappedBy = "specialty")
    private List<DoctorSpecialty> doctorSpecialtyList;

    public Specialty() {
    }
}
