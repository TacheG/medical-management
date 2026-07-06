package com.medical.backend.controller;

import com.medical.backend.request.DoctorRequest;
import com.medical.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/request-doctor/{id}")
    public String requestDoctor(@RequestBody DoctorRequest doctorRequest) {
        return userService.requestDoctor(doctorRequest);
    }
}