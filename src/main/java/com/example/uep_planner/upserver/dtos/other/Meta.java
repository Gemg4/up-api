package com.example.uep_planner.upserver.dtos.other;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Meta {
    private LocalDateTime timestamp;
}
