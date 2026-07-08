package com.medical.backend.controller;

import com.medical.backend.entity.DoctorSchedule;
import com.medical.backend.request.DoctorRequest;
import com.medical.backend.request.DoctorScheduleRequest;
import com.medical.backend.service.DoctorService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @PostMapping("/schedule")
    public String scheduleDoctor(Authentication authentication, @RequestBody DoctorScheduleRequest doctorRequest) {
        String username = authentication.getName();
        return doctorService.addSchedule(username, doctorRequest);
    }

    @GetMapping("/{doctor-id}/available-slots")
    public List<String> getAvailableSlots(@PathVariable("doctor-id") Long doctorId, @RequestParam LocalDate date) {
        return doctorService.getAvailableSlots(doctorId, date);
    }

}
