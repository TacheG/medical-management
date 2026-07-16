package com.medical.backend.controller;

import com.medical.backend.request.MedicalRecordRequest;
import com.medical.backend.dto.MedicalRecordDto;
import com.medical.backend.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {
    @Autowired
    private DoctorService doctorService;

    @PostMapping("/appointment/{appointmentId}")
    public String appointment(
            @PathVariable Long appointmentId,
            @RequestBody MedicalRecordRequest request,
            Authentication authentication
    ) {
        return doctorService.createMedicalRecord(
                appointmentId,
                request,
                authentication
        );
    }

    @GetMapping("/my-history")
    public List<MedicalRecordDto> getMedicalHistory(
            Authentication authentication
    ) {
        return doctorService.getMedicalHistory(authentication);
    }
}
