package com.example.uep_planner.upserver.services;

import com.example.uep_planner.upserver.dtos.contents.NewGoalDto;
import com.example.uep_planner.upserver.exceptions.ApiAssert;
import com.example.uep_planner.upserver.http.ResponseBuilder;
import com.example.uep_planner.upserver.mappers.ContentMapper;
import com.example.uep_planner.upserver.repositories.GoalJpaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GoalService {

    private final GoalJpaRepository goalRepo;
    private final UserService userService;
    private final ContentMapper contentMapper;

    public ResponseEntity<?> getAllGoals(Long id, String status) {
        var goals = this.goalRepo.findAllForUser(id, status);
        ApiAssert.notFoundIf(goals.isEmpty(), "No goals found");
        return ResponseBuilder.success("Goals fetch successful", goals);
    }

    public ResponseEntity<?> getGoalById(Long id, long goalId) {
        var goal = this.goalRepo.findByIdForUser(id, goalId).orElse(null);
        ApiAssert.notFoundIf(goal == null, "Goal not found");
        return ResponseBuilder.success("Goal fetch successful", goal);
    }

    @Transactional
    public ResponseEntity<?> postGoal(Long id, NewGoalDto newGoalDto) {
        var goal = this.contentMapper.toEntity(newGoalDto);
        goal.setUser(userService.getUserById(id));
        var savedGoal = this.goalRepo.save(goal);
        return ResponseBuilder.created("Goal created successful", savedGoal);
    }

    @Transactional
    public ResponseEntity<?> deleteGoalById(Long id, long goalId) {
        var goal = this.goalRepo.findByIdForUser(id, goalId).orElse(null);
        ApiAssert.notFoundIf(goal == null, "Goal not found");
        goal.setUser(null);
        this.goalRepo.delete(goal);
        return ResponseBuilder.success("Goal deleted successful", null);
    }

    @Transactional
    public ResponseEntity<?> updateGoalById(Long id, long goalId, NewGoalDto newGoalDto) {
        var goal = this.goalRepo.findByIdForUser(id, goalId).orElse(null);
        ApiAssert.notFoundIf(goal == null, "Goal not found");
        goal.setTitle(newGoalDto.getTitle());
        goal.setDescription(newGoalDto.getDescription());
        goal.setCategory(newGoalDto.getCategory());
        goal.setPriority(newGoalDto.getPriority());
        goal.setStatus("active");
        goal.setTargetDate(newGoalDto.getTargetDate());
        var updateGoal = this.goalRepo.save(goal);
        return ResponseBuilder.success("Goal updated successful", updateGoal);
    }

    public ResponseEntity<?> updateGoalStatusById(Long id, long goalId, String status) {
        var goal = this.goalRepo.findByIdForUser(id, goalId).orElse(null);
        ApiAssert.notFoundIf(goal == null, "Goal not found");
        goal.setStatus(status);
        var updatedGoal = this.goalRepo.save(goal);
        return ResponseBuilder.success("Goal " + status + " successful", updatedGoal);
    }

}
