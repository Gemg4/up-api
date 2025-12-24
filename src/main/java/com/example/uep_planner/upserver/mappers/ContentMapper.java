package com.example.uep_planner.upserver.mappers;

import com.example.uep_planner.upserver.dtos.contents.NewGoalDto;
import com.example.uep_planner.upserver.dtos.contents.NewMotivationDto;
import com.example.uep_planner.upserver.dtos.contents.NewScheduleDto;
import com.example.uep_planner.upserver.dtos.contents.NewTaskDto;
import com.example.uep_planner.upserver.entities.Goal;
import com.example.uep_planner.upserver.entities.Motivation;
import com.example.uep_planner.upserver.entities.Schedule;
import com.example.uep_planner.upserver.entities.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContentMapper {
    Goal toEntity(NewGoalDto newGoalDto);
    Task toEntity(NewTaskDto newTaskDto);
    Schedule toEntity(NewScheduleDto newScheduleDto);
    Motivation toEntity(NewMotivationDto newMotivationDto);
}