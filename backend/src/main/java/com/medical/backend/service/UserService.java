package com.medical.backend.service;

import com.medical.backend.entity.Doctor;
import com.medical.backend.entity.DoctorStatus;
import com.medical.backend.entity.Patient;
import com.medical.backend.entity.User;
import com.medical.backend.repository.DoctorRepository;
import com.medical.backend.repository.UserRepository;
import com.medical.backend.request.DoctorRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.beans.Transient;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Transactional
    public String requestDoctor(DoctorRequest doctorRequest) {
        Optional<User> user = userRepository.findById(doctorRequest.getUserId());

        if (user.isEmpty()) return "User not found";

        User userDetails = user.get();

        if (userDetails.isDoctor()) return "User is already a doctor";

        if (userDetails.isDoctorRequest()) return "User has already requested to become a doctor";

        Patient patient = userDetails.getPatientProfile();

        if (patient == null || patient.getCNP() == null || patient.getPhoneNumber() == null) return "Please complete important data before requesting to become a doctor";

        Doctor doctor = new Doctor();
        doctor.setUser(userDetails);
        doctor.setLicenseNumber(doctorRequest.getLicenseNumber());
        doctor.setStatus(DoctorStatus.PENDING);
        doctorRepository.save(doctor);

        userDetails.setDoctorRequest(true);
        userRepository.save(userDetails);

        userDetails.setDoctorProfile(doctor);

        return "Success, your application was submitted";
    }

    public String approveDoctor(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) return "User not found";

        User userDetails = user.get();
        Doctor doctor =  userDetails.getDoctorProfile();

        doctor.setStatus(DoctorStatus.APPROVED);
        doctorRepository.save(doctor);

        userDetails.setDoctorRequest(false);
        userDetails.setDoctor(true);
        userRepository.save(userDetails);

        return  "Success, your application was approved";
    }

    public String removeDoctor(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) return "User not found";

        User userDetails = user.get();
        Doctor doctor =  userDetails.getDoctorProfile();

        doctor.setStatus(DoctorStatus.REJECTED);
        doctorRepository.save(doctor);

        userDetails.setDoctorRequest(false);
        userDetails.setDoctor(false);
        userRepository.save(userDetails);

        return  "Success, doctor with id " + id + " has been removed";
    }
}