package com.example.uep_planner.upserver.services;

import com.example.uep_planner.upserver.dtos.contents.NewScheduleDto;
import com.example.uep_planner.upserver.exceptions.ApiAssert;
import com.example.uep_planner.upserver.http.ResponseBuilder;
import com.example.uep_planner.upserver.mappers.ContentMapper;
import com.example.uep_planner.upserver.repositories.ScheduleJpaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleJpaRepository scheduleRepo;
    private final UserService userService;
    private final ContentMapper contentMapper;

    public ResponseEntity<?> getAllSchedules(Long id, String day) {
        var schedules = this.scheduleRepo.findAllForUser(id, day);
        ApiAssert.notFoundIf(schedules.isEmpty(), "No schedules found");
        return ResponseBuilder.success("Schedules fetch successful", schedules);
    }

    public ResponseEntity<?> getScheduleById(Long id, long scheduleId) {
        var schedule = this.scheduleRepo.findByIdForUser(id, scheduleId).orElse(null);
        ApiAssert.notFoundIf(schedule == null, "Schedule not found");
        return ResponseBuilder.success("Schedule fetch successful", schedule);
    }

    @Transactional
    public ResponseEntity<?> postSchedule(Long id, NewScheduleDto newScheduleDto) {
        var schedule = this.contentMapper.toEntity(newScheduleDto);
        schedule.setUser(userService.getUserById(id));
        var savedSchedule = this.scheduleRepo.save(schedule);
        return ResponseBuilder.created("Schedule created successful", savedSchedule);
    }

    @Transactional
    public ResponseEntity<?> deleteScheduleById(Long id, long scheduleId) {
        var schedule = this.scheduleRepo.findByIdForUser(id, scheduleId).orElse(null);
        ApiAssert.notFoundIf(schedule == null, "Schedule not found");
        schedule.setUser(null);
        this.scheduleRepo.delete(schedule);
        return ResponseBuilder.success("Schedule deleted successful", null);
    }

    @Transactional
    public ResponseEntity<?> updateScheduleById(Long id, long scheduleId, NewScheduleDto newScheduleDto) {
        var schedule = this.scheduleRepo.findByIdForUser(id, scheduleId).orElse(null);
        ApiAssert.notFoundIf(schedule == null, "Schedule not found");
        schedule.setSubject(newScheduleDto.getSubject());
        schedule.setDay(newScheduleDto.getDay());
        schedule.setStartTime(newScheduleDto.getStartTime());
        schedule.setEndTime(newScheduleDto.getEndTime());
        schedule.setLocation(newScheduleDto.getLocation());
        schedule.setColor(newScheduleDto.getColor());
        var updateSchedule = this.scheduleRepo.save(schedule);
        return ResponseBuilder.success("Schedule updated successful", updateSchedule);
    }

}
