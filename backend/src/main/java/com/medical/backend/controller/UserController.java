package com.medical.backend.controller;

import com.medical.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/request-doctor/{id}")
    public String requestDoctor(@PathVariable Long id) {
        return userService.requestDoctor(id);
    }
}