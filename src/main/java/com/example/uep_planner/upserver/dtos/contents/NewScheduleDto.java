package com.example.uep_planner.upserver.dtos.contents;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewScheduleDto {

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Day is required")
    private String day;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Color is required")
    private String color;
}
