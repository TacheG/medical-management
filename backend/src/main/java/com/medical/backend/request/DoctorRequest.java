package com.medical.backend.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DoctorRequest {
    private String licenseNumber;
    private String biography;
    private Integer experienceYears;

}
