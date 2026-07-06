package com.medical.backend.controller;

import com.medical.backend.request.DoctorRequest;
import com.medical.backend.request.PatientProfileRequest;
import com.medical.backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PutMapping("/profile")
    public String updateProfile(Authentication authentication, @RequestBody PatientProfileRequest patientRequest) {
        String username = authentication.getName();
        return patientService.updateProfile(username, patientRequest);
    }
}
