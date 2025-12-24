package com.example.uep_planner.upserver.controllers;

import com.example.uep_planner.upserver.dtos.contents.NewTaskDto;
import com.example.uep_planner.upserver.helpers.ValidationHelper;
import com.example.uep_planner.upserver.services.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/my_websites/Projects/uep-planner/v1")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final ValidationHelper validateHelper;

    @GetMapping("/tasks")
    public ResponseEntity<?> getTasks(
            @AuthenticationPrincipal Long id,
            @RequestParam(required = false, name = "status") String status) {
        return this.taskService.getAllTasks(id, status);
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<?> getTaskById(@AuthenticationPrincipal Long id, @PathVariable long taskId) {
        return this.taskService.getTaskById(id, taskId);
    }

    @PostMapping("/tasks")
    public ResponseEntity<?> postTask(
            @AuthenticationPrincipal Long id,
            @ModelAttribute NewTaskDto newTaskDto) {
        this.validateHelper.validate(newTaskDto);
        return this.taskService.postTask(id, newTaskDto);
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<?> deleteTaskById(@AuthenticationPrincipal Long id, @PathVariable long taskId) {
        return this.taskService.deleteTaskById(id, taskId);
    }

    @PostMapping("/tasks/{taskId}")
    public ResponseEntity<?> UpdateTaskById(
            @AuthenticationPrincipal Long id,
            @PathVariable long taskId,
            @ModelAttribute NewTaskDto newTaskDto) {
        this.validateHelper.validate(newTaskDto);
        return this.taskService.updateTaskById(id, taskId, newTaskDto);
    }

    @PutMapping("/tasks/{taskId}")
    public ResponseEntity<?> updateTaskStatusById(@AuthenticationPrincipal Long id, @PathVariable long taskId, @RequestParam("status") String requestStatus) {
//        this.validateHelper.validate(requestStatus);
        return this.taskService.updateTaskStatusById(id, taskId, requestStatus);
    }

}
