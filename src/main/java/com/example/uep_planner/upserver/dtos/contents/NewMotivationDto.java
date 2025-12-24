package com.example.uep_planner.upserver.dtos.contents;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewMotivationDto {
    @NotBlank(message = "Content is required")
    private String content;
}
