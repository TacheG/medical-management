package com.medical.backend.controller;

import com.medical.backend.dto.DoctorDto;
import com.medical.backend.dto.DoctorScheduleDto;
import com.medical.backend.dto.PatientDto;
import com.medical.backend.entity.Doctor;
import com.medical.backend.entity.DoctorSchedule;
import com.medical.backend.entity.Patient;
import com.medical.backend.entity.User;
import com.medical.backend.repository.UserRepository;
import com.medical.backend.request.DoctorRequest;
import com.medical.backend.request.DoctorScheduleRequest;
import com.medical.backend.service.DoctorService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorService doctorService;

    @PutMapping("/profile")
    public String updateProfile(Authentication authentication, @RequestBody DoctorRequest doctorRequest) {
        String username = authentication.getName();
        return doctorService.updateProfile(username, doctorRequest);
    }

    @PostMapping("/schedule")
    public String scheduleDoctor(Authentication authentication, @RequestBody DoctorScheduleRequest doctorRequest) {
        String username = authentication.getName();
        return doctorService.addSchedule(username, doctorRequest);
    }

    @GetMapping("/getSchedule")
    public List<DoctorScheduleDto> getSchedule(Authentication authentication) {
        return doctorService.getSchedule(authentication);
    }

    @GetMapping("/{doctor-id}/available-slots")
    public List<String> getAvailableSlots(@PathVariable("doctor-id") Long doctorId, @RequestParam LocalDate date) {
        return doctorService.getAvailableSlots(doctorId, date);
    }

    @GetMapping("/getProfile")
    public DoctorDto getProfile(Authentication authentication) {
        String username = authentication.getName();

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) return null;

        Doctor doctor = user.get().getDoctorProfile();

        if (doctor == null) return null;

        return new DoctorDto(
                user.get().getUsername(),
                user.get().getEmail(),
                doctor.getBiography(),
                doctor.getExperienceYears(),
                doctor.getLicenseNumber()
        );
    }



}
