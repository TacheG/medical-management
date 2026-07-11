package com.medical.backend.service;

import com.medical.backend.dto.DoctorDto;
import com.medical.backend.dto.DoctorScheduleDto;
import com.medical.backend.entity.Doctor;
import com.medical.backend.entity.DoctorSchedule;
import com.medical.backend.entity.User;
import com.medical.backend.repository.DoctorRepository;
import com.medical.backend.repository.DoctorScheduleRepository;
import com.medical.backend.repository.UserRepository;
import com.medical.backend.request.DoctorRequest;
import com.medical.backend.request.DoctorScheduleRequest;
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
}
