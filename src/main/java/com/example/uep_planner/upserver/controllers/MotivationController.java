package com.example.uep_planner.upserver.controllers;

import com.example.uep_planner.upserver.dtos.contents.NewMotivationDto;
import com.example.uep_planner.upserver.helpers.ValidationHelper;
import com.example.uep_planner.upserver.services.MotivationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/my_websites/Projects/uep-planner/v1")
@AllArgsConstructor
public class MotivationController {

    private final MotivationService motivationService;
    private final ValidationHelper validateHelper;

    @GetMapping("/motivations")
    public ResponseEntity<?> getMotivations(
            @AuthenticationPrincipal Long id,
            @RequestParam(required = false, name = "status") String status) {
        return this.motivationService.getAllMotivations(id, status);
    }

    @GetMapping("/motivations/{motivationId}")
    public ResponseEntity<?> getMotivationById(@AuthenticationPrincipal Long id, @PathVariable long motivationId) {
        return this.motivationService.getMotivationById(id, motivationId);
    }

    @PostMapping("/motivations")
    public ResponseEntity<?> postMotivation(
            @AuthenticationPrincipal Long id,
            @ModelAttribute NewMotivationDto newMotivationDto) {
        this.validateHelper.validate(newMotivationDto);
        return this.motivationService.postMotivation(id, newMotivationDto);
    }

    @DeleteMapping("/motivations/{motivationId}")
    public ResponseEntity<?> deleteMotivationById(@AuthenticationPrincipal Long id, @PathVariable long motivationId) {
        return this.motivationService.deleteMotivationById(id, motivationId);
    }

    @PostMapping("/motivations/{motivationId}")
    public ResponseEntity<?> UpdateMotivationById(
            @AuthenticationPrincipal Long id,
            @PathVariable long motivationId,
            @ModelAttribute NewMotivationDto newMotivationDto) {
        this.validateHelper.validate(newMotivationDto);
        return this.motivationService.updateMotivationById(id, motivationId, newMotivationDto);
    }

}
