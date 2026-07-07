package com.medical.backend.service;

import com.medical.backend.entity.Doctor;
import com.medical.backend.entity.User;
import com.medical.backend.repository.UserRepository;
import com.medical.backend.request.DoctorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private UserRepository userRepository;

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
}
