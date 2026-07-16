package com.medical.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DoctorDto {
    private String username;
    private String email;
    private String biography;
    private Integer experienceYears;
    private String licenseNumber;

    private  List<DoctorSpecialtyDto> specialty;
}
