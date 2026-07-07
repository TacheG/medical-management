package com.medical.backend.controller;

import com.medical.backend.request.DoctorRequest;
import com.medical.backend.service.DoctorService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PutMapping("/profile")
    public String updateProfile(Authentication authentication, @RequestBody DoctorRequest doctorRequest) {
        String username = authentication.getName();
        return doctorService.updateProfile(username, doctorRequest);
    }

}
