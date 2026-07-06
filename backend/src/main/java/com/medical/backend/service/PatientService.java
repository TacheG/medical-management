package com.medical.backend.service;

import com.medical.backend.entity.Patient;
import com.medical.backend.entity.User;
import com.medical.backend.repository.UserRepository;
import com.medical.backend.request.PatientProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private UserRepository userRepository;

    public String updateProfile(String username, PatientProfileRequest patientRequest) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty()) return "User not found";

        User userDetails = user.get();

        Patient patientProfile = userDetails.getPatientProfile();
        if (patientProfile == null) return "User not a patient";

        patientProfile.setCNP(patientRequest.getCnp());
        patientProfile.setPhoneNumber(patientRequest.getPhoneNumber());

        userRepository.save(userDetails);
        return "Profile updated successfully";
    }
}
