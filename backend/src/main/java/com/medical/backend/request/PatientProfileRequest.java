package com.medical.backend.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class PatientProfileRequest {
    private String cnp;
    private String phoneNumber;
    private String bloodType;
    private String allergies;
    private LocalDate dateOfBirth;
}
