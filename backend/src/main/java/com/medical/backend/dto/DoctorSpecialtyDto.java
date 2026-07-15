package com.medical.backend.dto;

import com.medical.backend.entity.SpecialtyType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class DoctorSpecialtyDto {
    private Long id;
    private SpecialtyType specialtyType;
    private BigDecimal price;

    public DoctorSpecialtyDto(Long id, SpecialtyType specialtyType, BigDecimal price) {
        this.id = id;
        this.specialtyType = specialtyType;
        this.price = price;
    }
}
