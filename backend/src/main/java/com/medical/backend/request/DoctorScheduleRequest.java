package com.medical.backend.request;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Setter
@Getter
public class DoctorScheduleRequest {

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

}
