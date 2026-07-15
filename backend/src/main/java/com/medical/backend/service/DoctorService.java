package com.medical.backend.service;

import com.medical.backend.dto.DoctorDto;
import com.medical.backend.dto.DoctorScheduleDto;
import com.medical.backend.dto.DoctorSpecialtyDto;
import com.medical.backend.entity.*;
import com.medical.backend.repository.*;
import com.medical.backend.request.DoctorRequest;
import com.medical.backend.request.DoctorScheduleRequest;
import com.medical.backend.request.DoctorSpecialtyRequest;
import com.medical.backend.request.MedicalRecordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DoctorScheduleRepository doctorScheduleRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorSpecialtyRepository doctorSpecialtyRepository;

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public String updateProfile(String username, DoctorRequest doctorRequest) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) return "User not found";

        User userDetails = user.get();

        Doctor doctorProfile = userDetails.getDoctorProfile();

        if (doctorProfile == null) return "User not a doctor";

        doctorProfile.setBiography(doctorRequest.getBiography());
        doctorProfile.setExperienceYears(doctorRequest.getExperienceYears());

        userRepository.save(userDetails);
        return "Profile updated!";
    }

    public List<DoctorScheduleDto> getSchedule(Authentication authentication) {
        String username = authentication.getName();

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) return new ArrayList<>();

        User userDetails = user.get();

        Doctor doctor =  userDetails.getDoctorProfile();

        if (doctor == null) return new ArrayList<>();

        return  doctorScheduleRepository.findByDoctorId(doctor.getId())
                .stream()
                .map(schedule -> new DoctorScheduleDto(
                        schedule.getDayOfWeek(),
                        schedule.getStartTime(),
                        schedule.getEndTime()
                ))
                .collect(Collectors.toList());
    }

    public List<DoctorDto> findAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private DoctorDto toDto(Doctor doctor) {
        return new DoctorDto(
                doctor.getUser().getUsername(),
                doctor.getUser().getEmail(),
                doctor.getBiography(),
                doctor.getExperienceYears(),
                doctor.getLicenseNumber()
        );
    }

    public String addSchedule(String username, DoctorScheduleRequest doctorScheduleRequest) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) return "User not found";

        User userDetails = user.get();

        Doctor doctorProfile = userDetails.getDoctorProfile();

        if (!userDetails.isDoctor() || doctorProfile == null) return "User not a doctor";

        if (doctorScheduleRequest.getStartTime().isAfter(doctorScheduleRequest.getEndTime())) return "StartTime should be before endTime";

        DoctorSchedule schedule = doctorScheduleRepository.findByDoctorAndDayOfWeek(doctorProfile, doctorScheduleRequest.getDayOfWeek())
                .orElse(new DoctorSchedule());

        schedule.setDoctor(doctorProfile);
        schedule.setDayOfWeek(doctorScheduleRequest.getDayOfWeek());
        schedule.setStartTime(doctorScheduleRequest.getStartTime());
        schedule.setEndTime(doctorScheduleRequest.getEndTime());

        doctorScheduleRepository.save(schedule);

        return "Schedule added!";
    }

    public List<String> getAvailableSlots(Long doctorId, LocalDate date) {
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);

        if (doctor.isEmpty()) return List.of("Doctor not found");

        DayOfWeek dayOfWeek = date.getDayOfWeek();

        Optional<DoctorSchedule> schedule = doctorScheduleRepository.findByDoctorAndDayOfWeek(doctor.get(), dayOfWeek);

        if (schedule.isEmpty()) {
            return List.of("Doctor does not work on this day");
        }

        List<String> slots = new ArrayList<>();

        LocalTime current = schedule.get().getStartTime();

        while (current.isBefore(schedule.get().getEndTime())) {
            slots.add(current.toString());
            current = current.plusMinutes(30);
        }
        return slots;
    }

    public String createMedicalRecord(Long appointmentId, MedicalRecordRequest request) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);

        if (appointment.isEmpty()) {
            return "Appointment does not exist";
        }

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setAppointment(appointment.get());
        medicalRecord.setDiagnosis(request.getDiagnosis());
        medicalRecord.setTreatment(request.getTreatment());
        medicalRecord.setDoctorNotes(request.getDoctorNotes());

        appointment.get().setMedicalRecord(medicalRecord);
        appointment.get().setAppointmentStatus(AppointmentStatus.COMPLETED);

        appointmentRepository.save(appointment.get());

        return "Medical Record created!";
    }

    public String addSpecialty(String username, DoctorSpecialtyRequest doctorSpecialtyRequest) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) return "User not found";

        User userDetails = user.get();

        Doctor doctor = userDetails.getDoctorProfile();

        if (doctor == null) return "User not a doctor";

        if (doctorSpecialtyRepository.existsByDoctorAndSpecialtyType(doctor, doctorSpecialtyRequest.getSpecialtyType()))
            return "Specialty already exists";

        DoctorSpecialty specialty = new DoctorSpecialty();

        specialty.setDoctor(doctor);
        specialty.setSpecialtyType(doctorSpecialtyRequest.getSpecialtyType());
        specialty.setPrice(doctorSpecialtyRequest.getPrice());

        doctorSpecialtyRepository.save(specialty);

        return "Specialty added!";
    }

    public List<DoctorSpecialtyDto> getSpecialties(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) return List.of();

        User userDetails = user.get();

        Doctor doctor =  userDetails.getDoctorProfile();

        return doctorSpecialtyRepository.findByDoctor(doctor)
                .stream()
                .map(s -> new DoctorSpecialtyDto(
                        s.getId(),
                        s.getSpecialtyType(),
                        s.getPrice()
                ))
                .toList();
    }

    public String deleteSpecialty(String username, Long id) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) return "User not found";

        Doctor doctor = user.get().getDoctorProfile();

        if (doctor == null) return "User not a doctor";

        Optional<DoctorSpecialty> specialty = doctorSpecialtyRepository.findById(id);

        if (specialty.isEmpty()) return "Specialty not found";

        DoctorSpecialty doctorSpecialty = specialty.get();

        if (!doctorSpecialty.getDoctor().getId().equals(doctor.getId()))
            return "Not your specialty";

        doctorSpecialtyRepository.delete(doctorSpecialty);

        return "Specialty deleted!";
    }
}
