package com.medical.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DoctorDto {
    private Long id;
    private String username;
    private String biography;
    private Integer experienceYears;
    private String licenseNumber;
}
