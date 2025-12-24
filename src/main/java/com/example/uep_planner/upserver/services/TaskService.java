package com.example.uep_planner.upserver.services;

import com.example.uep_planner.upserver.dtos.contents.NewTaskDto;
import com.example.uep_planner.upserver.exceptions.ApiAssert;
import com.example.uep_planner.upserver.http.ResponseBuilder;
import com.example.uep_planner.upserver.mappers.ContentMapper;
import com.example.uep_planner.upserver.repositories.TaskJpaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskJpaRepository taskRepo;
    private final UserService userService;
    private final ContentMapper contentMapper;

    public ResponseEntity<?> getAllTasks(Long id, String status) {
        var tasks = this.taskRepo.findAllForUser(id, status);
        ApiAssert.notFoundIf(tasks.isEmpty(), "No tasks found");
        return ResponseBuilder.success("Tasks fetch successful", tasks);
    }

    public ResponseEntity<?> getTaskById(Long id, long taskId) {
        var task = this.taskRepo.findByIdForUser(id, taskId).orElse(null);
        ApiAssert.notFoundIf(task == null, "Task not found");
        return ResponseBuilder.success("Task fetch successful", task);
    }

    @Transactional
    public ResponseEntity<?> postTask(Long id, NewTaskDto newTaskDto) {
        var task = this.contentMapper.toEntity(newTaskDto);
        task.setUser(userService.getUserById(id));
        var savedTask = this.taskRepo.save(task);
        return ResponseBuilder.created("Task created successful", savedTask);
    }

    @Transactional
    public ResponseEntity<?> deleteTaskById(Long id, long taskId) {
        var task = this.taskRepo.findByIdForUser(id, taskId).orElse(null);
        ApiAssert.notFoundIf(task == null, "Task not found");
        task.setUser(null);
        this.taskRepo.delete(task);
        return ResponseBuilder.success("Task deleted successful", null);
    }

    @Transactional
    public ResponseEntity<?> updateTaskById(Long id, long taskId, NewTaskDto newTaskDto) {
        var task = this.taskRepo.findByIdForUser(id, taskId).orElse(null);
        ApiAssert.notFoundIf(task == null, "Task not found");
        task.setDescription(newTaskDto.getDescription());
        task.setTargetDate(newTaskDto.getTargetDate());
        task.setStatus("pending");
        var updateTask = this.taskRepo.save(task);
        return ResponseBuilder.success("Task updated successful", updateTask);
    }

    public ResponseEntity<?> updateTaskStatusById(Long id, long taskId, String status) {
        var task = this.taskRepo.findByIdForUser(id, taskId).orElse(null);
        ApiAssert.notFoundIf(task == null, "Task not found");
        task.setStatus(status);
        var updatedTask = this.taskRepo.save(task);
        return ResponseBuilder.success("Task " + status + " successful", updatedTask);
    }

}
