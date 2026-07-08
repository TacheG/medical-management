package com.medical.backend.controller;

import com.medical.backend.dto.DoctorDto;
import com.medical.backend.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UtilsController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/doctors")
    public List<DoctorDto> getAllDoctors() {
        return doctorService.findAllDoctors();
    }
}
