package com.medical.backend.service;

import com.medical.backend.entity.*;
import com.medical.backend.repository.*;
import com.medical.backend.request.AppointmentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorScheduleRepository doctorScheduleRepository;

    public String createAppointment(AppointmentRequest appointmentRequest) {
        Doctor doctor = doctorRepository.findById(appointmentRequest.getDoctorId())
                .orElse(null);

        if (doctor == null)
            return "Doctor not found.";

        Patient patient = patientRepository.findById(appointmentRequest.getPatientId())
                .orElse(null);

        if (patient == null)
            return "Patient not found.";

        DayOfWeek day = appointmentRequest.getAppointmentDateTime().getDayOfWeek();
        LocalTime time = appointmentRequest.getAppointmentDateTime().toLocalTime();

        DoctorSchedule doctorSchedule = doctorScheduleRepository
                .findByDoctorAndDayOfWeek(doctor, day)
                .orElse(null);

        if (doctorSchedule == null)
            return "Doctor is not working on that day.";

        if (time.isBefore(doctorSchedule.getStartTime()) || time.isAfter(doctorSchedule.getEndTime()))
            return "Doctor is not working at that hour.";

        boolean exists = appointmentRepository.existsByDoctorIdAndAppointmentDateTime(
                appointmentRequest.getDoctorId(),
                appointmentRequest.getAppointmentDateTime()
        );

        if (exists)
            return "Doctor already has an appointment at that time.";

        Appointment appointment = new Appointment();

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentDateTime(
                appointmentRequest.getAppointmentDateTime()
        );

        appointment.setSymptomsDescription(
                appointmentRequest.getSymptomsDescription()
        );

        appointment.setAppointmentStatus(AppointmentStatus.PENDING);

        appointmentRepository.save(appointment);

        return "Appointment created successfully!";
    }
}
