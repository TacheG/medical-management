package com.medical.backend.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalRecordRequest {
    private String diagnosis;
    private String treatment;
    private String doctorNotes;
}
