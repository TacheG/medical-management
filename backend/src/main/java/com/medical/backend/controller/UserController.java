package com.medical.backend.controller;

import com.medical.backend.request.DoctorRequest;
import com.medical.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/request-doctor")
    public String requestDoctor(Authentication authentication, @RequestBody DoctorRequest doctorRequest) {
        return userService.requestDoctor(authentication, doctorRequest);
    }
}