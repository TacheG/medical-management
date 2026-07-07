package com.medical.backend.service;

import com.medical.backend.entity.Patient;
import com.medical.backend.entity.User;
import com.medical.backend.repository.UserRepository;
import com.medical.backend.request.PatientProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        if (patientRequest.getCnp() == null || patientRequest.getCnp().length() != 13)
            return "CNP should have exactly 13 digits";

        if (patientRequest.getPhoneNumber() == null) return "Phone number is required";
        if (patientRequest.getPhoneNumber().length() > 15) return "Phone Number invalid";

        if (patientRequest.getBloodType() == null) return "Blood type is required";
        if (patientRequest.getBloodType().length() > 2) return "Blood Type invalid";

        if (patientRequest.getDateOfBirth() == null)
            return "Date of birth is required";
        if (patientRequest.getDateOfBirth().isAfter(LocalDate.now()))
            return "Date of birth cannot be in the future";

        patientProfile.setCNP(patientRequest.getCnp());
        patientProfile.setPhoneNumber(patientRequest.getPhoneNumber());
        patientProfile.setBloodType(patientRequest.getBloodType());
        patientProfile.setAllergies(patientRequest.getAllergies());
        patientProfile.setDateOfBirth(patientRequest.getDateOfBirth());

        userRepository.save(userDetails);
        return "Profile updated successfully";
    }
}
