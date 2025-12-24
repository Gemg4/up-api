package com.example.uep_planner.upserver.controllers;

import com.example.uep_planner.upserver.dtos.contents.NewScheduleDto;
import com.example.uep_planner.upserver.helpers.ValidationHelper;
import com.example.uep_planner.upserver.services.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/my_websites/Projects/uep-planner/v1")
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ValidationHelper validateHelper;

    @GetMapping("/schedules")
    public ResponseEntity<?> getSchedules(
            @AuthenticationPrincipal Long id,
            @RequestParam(required = false, name = "day") String day) {
        return this.scheduleService.getAllSchedules(id, day);
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<?> getScheduleById(@AuthenticationPrincipal Long id, @PathVariable long scheduleId) {
        return this.scheduleService.getScheduleById(id, scheduleId);
    }

    @PostMapping("/schedules")
    public ResponseEntity<?> postSchedule(
            @AuthenticationPrincipal Long id,
            @ModelAttribute NewScheduleDto newScheduleDto) {
        this.validateHelper.validate(newScheduleDto);
        return this.scheduleService.postSchedule(id, newScheduleDto);
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<?> deleteScheduleById(@AuthenticationPrincipal Long id, @PathVariable long scheduleId) {
        return this.scheduleService.deleteScheduleById(id, scheduleId);
    }

    @PostMapping("/schedules/{scheduleId}")
    public ResponseEntity<?> UpdateScheduleById(
            @AuthenticationPrincipal Long id,
            @PathVariable long scheduleId,
            @ModelAttribute NewScheduleDto newScheduleDto) {
        this.validateHelper.validate(newScheduleDto);
        return this.scheduleService.updateScheduleById(id, scheduleId, newScheduleDto);
    }

}
