package com.example.uep_planner.upserver.services;

import com.example.uep_planner.upserver.dtos.contents.NewMotivationDto;
import com.example.uep_planner.upserver.exceptions.ApiAssert;
import com.example.uep_planner.upserver.http.ResponseBuilder;
import com.example.uep_planner.upserver.mappers.ContentMapper;
import com.example.uep_planner.upserver.repositories.MotivationJpaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MotivationService {

    private final MotivationJpaRepository motivationRepo;
    private final UserService userService;
    private final ContentMapper contentMapper;

    public ResponseEntity<?> getAllMotivations(Long id, String status) {
        var motivations = this.motivationRepo.findAllForUser(id, status);
        ApiAssert.notFoundIf(motivations.isEmpty(), "No motivations found");
        return ResponseBuilder.success("Motivations fetch successful", motivations);
    }

    public ResponseEntity<?> getMotivationById(Long id, long motivationId) {
        var motivation = this.motivationRepo.findByIdForUser(id, motivationId).orElse(null);
        ApiAssert.notFoundIf(motivation == null, "Motivation not found");
        return ResponseBuilder.success("Motivation fetch successful", motivation);
    }

    @Transactional
    public ResponseEntity<?> postMotivation(Long id, NewMotivationDto newMotivationDto) {
        var motivation = this.contentMapper.toEntity(newMotivationDto);
        motivation.setUser(userService.getUserById(id));
        var savedMotivation = this.motivationRepo.save(motivation);
        return ResponseBuilder.created("Motivation created successful", savedMotivation);
    }

    @Transactional
    public ResponseEntity<?> deleteMotivationById(Long id, long motivationId) {
        var motivation = this.motivationRepo.findByIdForUser(id, motivationId).orElse(null);
        ApiAssert.notFoundIf(motivation == null, "Motivation not found");
        motivation.setUser(null);
        this.motivationRepo.delete(motivation);
        return ResponseBuilder.success("Motivation deleted successful", null);
    }

    @Transactional
    public ResponseEntity<?> updateMotivationById(Long id, long motivationId, NewMotivationDto newMotivationDto) {
        var motivation = this.motivationRepo.findByIdForUser(id, motivationId).orElse(null);
        ApiAssert.notFoundIf(motivation == null, "Motivation not found");
        motivation.setContent(newMotivationDto.getContent());
        var updateMotivation = this.motivationRepo.save(motivation);
        return ResponseBuilder.success("Motivation updated successful", updateMotivation);
    }

}
