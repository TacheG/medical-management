package com.medical.backend.repository;

import com.medical.backend.entity.Doctor;
import com.medical.backend.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Long> {

    List<DoctorSchedule> findByDoctorId(Long doctorId);

    Optional<DoctorSchedule> findByDoctorAndDayOfWeek(Doctor doctor, DayOfWeek dayOfWeek);
}