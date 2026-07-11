package com.medical.backend.controller;

import com.medical.backend.dto.PatientDto;
import com.medical.backend.entity.Patient;
import com.medical.backend.entity.User;
import com.medical.backend.repository.UserRepository;
import com.medical.backend.request.PatientProfileRequest;
import com.medical.backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PatientService patientService;

    @PutMapping("/profile")
    public String updateProfile(Authentication authentication, @RequestBody PatientProfileRequest patientRequest) {

        if (authentication == null) {
            throw new RuntimeException("User not authenticated");
        }

        String username = authentication.getName();
        return patientService.updateProfile(username, patientRequest);
    }

    @GetMapping("/getProfile")
    public PatientDto getProfile(Authentication authentication) {
        String username = authentication.getName();

        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) return null;

        Patient patient = user.get().getPatientProfile();

        if (patient == null) return null;

        return new PatientDto(
                user.get().getUsername(),
                user.get().getEmail(),
                patient.getCNP(),
                patient.getPhoneNumber(),
                patient.getBloodType(),
                patient.getAllergies(),
                patient.getDateOfBirth()
        );
    }
}
