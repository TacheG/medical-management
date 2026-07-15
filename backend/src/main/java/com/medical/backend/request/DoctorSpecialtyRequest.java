package com.medical.backend.request;

import com.medical.backend.entity.SpecialtyType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DoctorSpecialtyRequest {

    private SpecialtyType specialtyType;

    private BigDecimal price;
}
