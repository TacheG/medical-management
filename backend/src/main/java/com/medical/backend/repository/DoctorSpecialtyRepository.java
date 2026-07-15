package com.medical.backend.repository;

import com.medical.backend.entity.Doctor;
import com.medical.backend.entity.DoctorSpecialty;
import com.medical.backend.entity.SpecialtyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorSpecialtyRepository extends JpaRepository<DoctorSpecialty, Long> {
    List<DoctorSpecialty> findByDoctor(Doctor doctor);

    boolean existsByDoctorAndSpecialtyType( Doctor doctor,  SpecialtyType specialtyType);
}
