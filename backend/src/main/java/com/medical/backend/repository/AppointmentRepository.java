package com.medical.backend.repository;

import com.medical.backend.entity.Appointment;
import com.medical.backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorIdAndAppointmentDateTime(
            Long doctorId,
            LocalDateTime appointmentDateTime
    );

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorAndAppointmentDateTimeBetween(
            Doctor doctor,
            LocalDateTime start,
            LocalDateTime end
    );

    List<Appointment> findByDoctor(Doctor doctor);
}
