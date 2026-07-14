package com.medical.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MedicalRecordDto {
    private Long id;

    private LocalDateTime appointmentDate;

    private String diagnosis;

    private String treatment;

    private String doctorNotes;

    private String doctorName;

    public MedicalRecordDto(Long id, String username, LocalDateTime appointmentDateTime, String diagnosis, String treatment, String doctorNotes) {
        this.id = id;
        this.appointmentDate = appointmentDateTime;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.doctorName = username;
        this.doctorNotes = doctorNotes;
    }
}
