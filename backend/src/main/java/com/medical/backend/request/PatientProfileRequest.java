package com.medical.backend.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientProfileRequest {
    private String cnp;
    private String phoneNumber;
}
