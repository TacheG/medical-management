package com.medical.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PatientDto {
    private String username;
    private String email;
    private String cnp;
    private String phoneNumber;
    private String bloodType;
    private String allergies;
    private LocalDate dateOfBirth;
}
