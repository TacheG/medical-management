package com.medical.backend.controller;

import com.medical.backend.request.MedicalRecordRequest;
import com.medical.backend.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {
    @Autowired
    private DoctorService doctorService;

    @PostMapping("/appointment/{appointmentId}")
    public String appointment(@PathVariable Long appointmentId, @RequestBody MedicalRecordRequest request) {
        return doctorService.createMedicalRecord(appointmentId, request);
    }
}
