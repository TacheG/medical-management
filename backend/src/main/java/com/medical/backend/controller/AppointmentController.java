package com.medical.backend.controller;

import com.medical.backend.dto.AppointmentDto;
import com.medical.backend.request.AppointmentRequest;
import com.medical.backend.request.AppointmentStatusRequest;
import com.medical.backend.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public String createAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        return appointmentService.createAppointment((appointmentRequest));
    }

    @GetMapping("/my-appointments")
    public List<AppointmentDto> getMyAppointments(Authentication authentication) {
        return appointmentService.getMyAppointments(authentication);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateAppointmentStatus (
            @PathVariable Long id,
            @RequestBody AppointmentStatusRequest request,
            Authentication authentication
    ) {

        String result = appointmentService.updateAppointmentStatus(
                id,
                request.getStatus(),
                authentication
        );

        return ResponseEntity.ok(result);
    }
}
