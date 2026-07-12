package com.medical.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentDto {

    private Long id;
    private LocalDateTime appointmentDateTime;
    private String appointmentStatus;
    private String symptomsDescription;
    private Long doctorId;
    private Long patientId;
}
