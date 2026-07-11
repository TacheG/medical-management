package com.medical.backend.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentRequest {
    private Long doctorId;
    private Long patientId;
    private LocalDateTime appointmentDateTime;
    private String symptomsDescription;
}
