package com.medical.backend.service;

import com.medical.backend.dto.AppointmentDto;
import com.medical.backend.entity.*;
import com.medical.backend.repository.*;
import com.medical.backend.request.AppointmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorScheduleRepository doctorScheduleRepository;

    public String createAppointment(
            AppointmentRequest appointmentRequest,
            Authentication authentication
    ) {

        if(authentication == null){
            return "No authentication.";
        }

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (user.isDoctor())
            return "Only patients can create appointments.";

        Patient patient = user.getPatientProfile();

        if (patient == null)
            return "Patient profile not found.";

        if(appointmentRequest.getDoctorId() == null){
            return "Doctor id is missing.";
        }

        Doctor doctor = doctorRepository.findById(
                        appointmentRequest.getDoctorId()
                )
                .orElse(null);

        if(doctor == null)
            return "Doctor not found.";

        if(appointmentRequest.getAppointmentDateTime() == null){
            return "Date is missing.";
        }

        DayOfWeek day = appointmentRequest.getAppointmentDateTime().getDayOfWeek();
        LocalTime time = appointmentRequest.getAppointmentDateTime().toLocalTime();

        DoctorSchedule doctorSchedule = doctorScheduleRepository
                .findByDoctorAndDayOfWeek(doctor, day)
                .orElse(null);

        if (doctorSchedule == null)
            return "Doctor is not working on that day.";

        if (time.isBefore(doctorSchedule.getStartTime())
                || !time.isBefore(doctorSchedule.getEndTime()))
            return "Doctor is not working at that hour.";

        boolean exists = appointmentRepository.existsByDoctorIdAndAppointmentDateTime(
                doctor.getId(),
                appointmentRequest.getAppointmentDateTime()
        );

        if (exists)
            return "Doctor already has an appointment at that time.";

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDateTime(appointmentRequest.getAppointmentDateTime());
        appointment.setSymptomsDescription(appointmentRequest.getSymptomsDescription());
        appointment.setAppointmentStatus(AppointmentStatus.PENDING);

        appointmentRepository.save(appointment);

        return "Appointment created successfully!";
    }

    public List<AppointmentDto> getMyAppointments(Authentication authentication){

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (user.isDoctor()) {

            Doctor doctor = doctorRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Doctor not found."));

            LocalDate today = LocalDate.now();

            LocalDateTime start = today.atStartOfDay();
            LocalDateTime end = today.atTime(LocalTime.MAX);

            return appointmentRepository
                    .findByDoctorAndAppointmentDateTimeBetween(
                            doctor,
                            start,
                            end
                    )
                    .stream()
                    .map(this::convertToDto)
                    .toList();

        } else {

            Patient patient = patientRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Patient not found."));

            return appointmentRepository
                    .findByPatientId(patient.getId())
                    .stream()
                    .map(this::convertToDto)
                    .toList();
        }
    }

    public String updateAppointmentStatus(
            Long appointmentId,
            String status,
            Authentication authentication
    ) {

        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (!user.isDoctor())
            return "Only doctors can change appointment status.";

        Doctor doctor = doctorRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Doctor not found."));

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found."));

        if (!appointment.getDoctor().getId().equals(doctor.getId()))
            return "You cannot modify this appointment.";

        try {

            AppointmentStatus newStatus = AppointmentStatus.valueOf(status.toUpperCase());

            appointment.setAppointmentStatus(newStatus);

            appointmentRepository.save(appointment);

            return "Appointment status updated successfully!";

        } catch (IllegalArgumentException e) {

            return "Invalid status.";
        }
    }

    private AppointmentDto convertToDto(Appointment appointment) {

        AppointmentDto dto = new AppointmentDto();

        dto.setId(appointment.getId());
        dto.setAppointmentDateTime(appointment.getAppointmentDateTime());
        dto.setAppointmentStatus(appointment.getAppointmentStatus().name());
        dto.setSymptomsDescription(appointment.getSymptomsDescription());
        dto.setDoctorId(appointment.getDoctor().getId());
        dto.setPatientId(appointment.getPatient().getId());

        return dto;
    }
}
