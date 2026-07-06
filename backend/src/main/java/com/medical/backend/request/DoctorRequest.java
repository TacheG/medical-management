package com.medical.backend.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DoctorRequest {
    private Long userId;
    private List<String> specialization;
    private String licenseNumber;

}
