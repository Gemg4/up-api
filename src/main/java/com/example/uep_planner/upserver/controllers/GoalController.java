package com.example.uep_planner.upserver.controllers;

import com.example.uep_planner.upserver.dtos.contents.NewGoalDto;
import com.example.uep_planner.upserver.helpers.ValidationHelper;
import com.example.uep_planner.upserver.services.GoalService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/my_websites/Projects/uep-planner/v1")
@AllArgsConstructor
public class GoalController {

    private final GoalService goalService;
    private final ValidationHelper validateHelper;

    @GetMapping("/goals")
    public ResponseEntity<?> getGoals(
            @AuthenticationPrincipal Long id,
            @RequestParam(required = false, name = "status") String status) {
        return this.goalService.getAllGoals(id, status);
    }

    @GetMapping("/goals/{goalId}")
    public ResponseEntity<?> getGoalById(@AuthenticationPrincipal Long id, @PathVariable long goalId) {
        return this.goalService.getGoalById(id, goalId);
    }

    @PostMapping("/goals")
    public ResponseEntity<?> postGoal(
            @AuthenticationPrincipal Long id,
            @ModelAttribute NewGoalDto newGoalDto
            ) {
        this.validateHelper.validate(newGoalDto);
        return this.goalService.postGoal(id, newGoalDto);
    }

    @DeleteMapping("/goals/{goalId}")
    public ResponseEntity<?> deleteGoalById(@AuthenticationPrincipal Long id, @PathVariable long goalId) {
        return this.goalService.deleteGoalById(id, goalId);
    }

    @PostMapping("/goals/{goalId}")
    public ResponseEntity<?> UpdateGoalById(
            @AuthenticationPrincipal Long id,
            @PathVariable long goalId,
            @ModelAttribute NewGoalDto newGoalDto) {
        this.validateHelper.validate(newGoalDto);
        return this.goalService.updateGoalById(id, goalId, newGoalDto);
    }

    @PutMapping("/goals/{goalId}")
    public ResponseEntity<?> updateGoalStatusById(@AuthenticationPrincipal Long id, @PathVariable long goalId, @RequestParam("status") String requestStatus) {
//        this.validateHelper.validate(requestStatus);
        return this.goalService.updateGoalStatusById(id, goalId, requestStatus);
    }

}
