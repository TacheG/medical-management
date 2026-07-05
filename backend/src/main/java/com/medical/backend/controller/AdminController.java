package com.medical.backend.controller;

import com.medical.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/approve-doctor/{id}")
    public String approveDoctor(@PathVariable Long id) {
        return userService.approveDoctor(id);
    }
}