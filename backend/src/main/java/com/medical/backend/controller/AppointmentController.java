package com.medical.backend.controller;

import com.medical.backend.repository.AppointmentRepository;
import com.medical.backend.request.AppointmentRequest;
import com.medical.backend.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public String createAppointment(@RequestBody AppointmentRequest appointmentRequest){
        return appointmentService.createAppointment((appointmentRequest));
    }
}
